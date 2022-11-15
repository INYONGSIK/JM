package com.ucamp.JM.config;

import com.ucamp.JM.dto.Music;
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
import java.util.List;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class WeeklyJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private final MusicServiceImpl musicService;
    private static final String WEEKLY = "SELECT music_number,music_title,music_singer,music_genre,music_release," +
            "music_image,music_file,music_lyrics,music_like FROM ACCUMUL";

    @Bean
    public Job weeklyJob() {
        return jobBuilderFactory.get("weeklyJob")
                .incrementer(new RunIdIncrementer())
                .start(insertWeeklyMusicStep())
                .build();
    }


    @JobScope
    @Bean
    public Step insertWeeklyMusicStep() {
        return stepBuilderFactory.get("updateTodayMusicStep")
                .<Music, Music>chunk(10)
                .reader(insertWeeklyMusicReader())
                .writer(insertWeeklyMusicWriter())
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<Music> insertWeeklyMusicReader() {
        return new JdbcCursorItemReaderBuilder<Music>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Music.class))
                .sql(WEEKLY)
                .name("jdbcCursorItemReader")
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<Music> insertWeeklyMusicWriter() {
        return new ItemWriter<Music>() {
            @Override
            public void write(List<? extends Music> items) throws Exception {
                items.forEach(musicService::insertWeekMusic);
            }
        };
    }
}
