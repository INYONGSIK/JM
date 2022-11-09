package com.ucamp.JM.config;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.service.MusicService;
import lombok.AllArgsConstructor;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@Slf4j
@AllArgsConstructor
public class UpdateRankJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private DataSource dataSource;
    private MusicService musicService;
    //todo: order by music like
    private static final String RANK_QUERY =
            "SELECT " +
                    "music_title, " +
                    "music_singer, " +
                    "music_genre, " +
                    "music_like " +
                    "FROM MUSIC " +
                    "WHERE ROWNUM <= 10";

    @Bean
    public Job updateRankJob(Step updateRankStep) {
        return jobBuilderFactory.get("updateRankJob")
                .incrementer(new RunIdIncrementer())
                .start(updateRankStep)
                .build();
    }

    @JobScope
    @Bean
    public Step updateRankStep(JdbcCursorItemReader<Music> updateRankReader,
                               ItemWriter<Music> updateRankReaderWriter
    ) {
        return stepBuilderFactory.get("updateRankStep")
                .<Music, Music>chunk(10)
                .reader(updateRankReader)
                .writer(updateRankReaderWriter)
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<Music> updateRankReaderWriter() {
        return items -> items.forEach(musicService::insertTodayMusic);
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<Music> updateRankReader() {
        return new JdbcCursorItemReaderBuilder<Music>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Music.class))
                .sql(RANK_QUERY)
                .name("jdbcCursorItemReader")
                .build();
    }

}
