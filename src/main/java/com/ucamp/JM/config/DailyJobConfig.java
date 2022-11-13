package com.ucamp.JM.config;

import com.ucamp.JM.dto.AccumulMusic;
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
import org.springframework.batch.item.ItemProcessor;
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
public class DailyJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private final MusicServiceImpl musicService;
    private static final String ACCUMULATE =
            "SELECT A.*, B.current_music_number,B.current_music_like,C.accmullike " +
                    "FROM today_music A, " +
                    "(SELECT music_number as current_music_number, music_like as current_music_like FROM music) B, " +
                    "(SELECT music_number,music_like as accmullike FROM accumul) C " +
                    "where A.music_number = B.current_music_number and A.music_number = C.music_number";

    private static final String TODAY = "SELECT * FROM MUSIC";

    /*@Bean
    public Job dailyjob() {
        return jobBuilderFactory.get("dailyjob")
                .incrementer(new RunIdIncrementer())
                .start(accumulateStep())
                .next(updateTodayMusicStep())
                .build();
    }*/
    @Bean(name = "dailyjob")
    public Job dailyjob() {
        return jobBuilderFactory.get("dailyjob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @JobScope
    @Bean
    public Step step(
    ) {
        return stepBuilderFactory.get("updateTodayMusicStep")
                .<Music, Music>chunk(10)
                .reader(updateTodayMusicReader())
                .writer(items -> {
                    System.out.println("1");
                })
                .build();
    }

    @JobScope
    @Bean
    public Step updateTodayMusicStep(
    ) {
        return stepBuilderFactory.get("updateTodayMusicStep")
                .<Music, Music>chunk(10)
                .reader(updateTodayMusicReader())
                .writer(updateTodayMusicWriter())
                .build();
    }

    @JobScope
    @Bean
    public Step accumulateStep(
    ) {
        return stepBuilderFactory.get("accumulateStep")
                .<AccumulMusic, AccumulMusic>chunk(10)
                .reader(accumulateReader())
                .processor(accumulateprocessor())
                .writer(accumulateWriter())
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<Music> updateTodayMusicReader() {
        return new JdbcCursorItemReaderBuilder<Music>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Music.class))
                .sql(TODAY)
                .name("jdbcCursorItemReader")
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<AccumulMusic> accumulateReader() {
        return new JdbcCursorItemReaderBuilder<AccumulMusic>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(AccumulMusic.class))
                .sql(ACCUMULATE)
                .name("jdbcCursorItemReader")
                .build();
    }


    @StepScope
    @Bean
    public ItemProcessor<AccumulMusic, AccumulMusic> accumulateprocessor() {
        return new ItemProcessor<AccumulMusic, AccumulMusic>() {
            @Override
            public AccumulMusic process(AccumulMusic music) throws Exception {
                music.setMusic_like(music.getAccumul_music_like() + (music.getCurrent_music_like() - music.getMusic_like()));
                log.info("MNo:" + music.getMusic_number() + "like:" + music.getMusic_like());
                return music;
            }
        };
    }

    @StepScope
    @Bean
    public ItemWriter<Music> updateTodayMusicWriter() {
        return items -> items.forEach(musicService::updateTodayMusic);
    }

    @StepScope
    @Bean
    public ItemWriter<AccumulMusic> accumulateWriter() {
        return items -> items.forEach(musicService::updateAccumulMusic);
    }

}
