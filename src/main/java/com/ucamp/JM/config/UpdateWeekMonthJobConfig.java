package com.ucamp.JM.config;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.service.MusicServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UpdateWeekMonthJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    private final MusicServiceImpl musicService;
    private static final String WEEK_MUSIC_QUERY =
            "SELECT A.*, B.current_music_number, B.current_music_like FROM week_music A, (SELECT music_number as current_music_number, " +
                    "music_like as current_music_like FROM accumul) B " +
                    "where A.music_number = B.current_music_number ";

    @Bean
    public Job updateWeekRankJob(Step updateWeekRankStep, Step updateMonthRankStep) {
        return jobBuilderFactory.get("updateWeekRankJob")
                .incrementer(new RunIdIncrementer())
                .start(updateMonthRankStep)
                .build();
    }

    @JobScope
    @Bean
    public Step updateWeekRankStep(Tasklet tasklet) {
        return stepBuilderFactory.get("updateWeekRankStep")
                .tasklet(tasklet)
                .build();
    }


    @Bean
    @JobScope
    public Tasklet tasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                musicService.updateAccumulMusicLikeToZero();
                return RepeatStatus.FINISHED;
            }
        };
    }

    @JobScope
    @Bean
    public Step updateMonthRankStep(JdbcCursorItemReader<AccumulMusic> updateMonthReader, ItemWriter<AccumulMusic> updateMonthWriter) {
        return stepBuilderFactory.get("updateMonthRankStep")
                .<AccumulMusic, AccumulMusic>chunk(10)
                .reader(updateMonthReader)
                .writer(updateMonthWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<AccumulMusic> updateMonthReader() {
        return new JdbcCursorItemReaderBuilder<AccumulMusic>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(AccumulMusic.class))
                .sql(WEEK_MUSIC_QUERY)
                .name("jdbcCursorItemReader")
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<AccumulMusic> updateMonthWriter() {
        return items -> items.forEach(musicService::insertMonthMusic);
    }

}
