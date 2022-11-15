package com.ucamp.JM.config;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.service.MusicServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MonthJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    private final MusicServiceImpl musicService;
    private static final String MONTH_MUSIC_QUERY =
            "select a.*,b.current_music_number,b.current_music_like " +
                    "from accumul a " +
                    ",(select max(music_number) as current_music_number, sum(music_like) as current_music_like from week_music group by music_number) b " +
                    "where a.music_number = b.current_music_number";

    @Bean
    public Job updateMonthJob() {
        return jobBuilderFactory.get("updateWeekRankJob")
                .incrementer(new RunIdIncrementer())
                .start(updateMonthStep())
                .build();
    }

    @JobScope
    @Bean
    public Step updateMonthStep() {
        return stepBuilderFactory.get("updateMonthRankStep")
                .<AccumulMusic, AccumulMusic>chunk(10)
                .reader(updateMonthReader())
                .writer(updateMonthWriter())
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<AccumulMusic> updateMonthReader() {
        return new JdbcCursorItemReaderBuilder<AccumulMusic>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(AccumulMusic.class))
                .sql(MONTH_MUSIC_QUERY)
                .name("jdbcCursorItemReader")
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<AccumulMusic> updateMonthWriter() {
        return items -> items.forEach(musicService::insertMonthMusic);
    }

}
