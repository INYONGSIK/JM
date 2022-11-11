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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class UpdateRankJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private final MusicServiceImpl musicService;


    //todo: 매주 월요일 : music table 캡쳐, 저장
    //todo: 매일 업데이트
    //todo: 매일 이전 좋아요와 비교해서 누적 좋아요수 업데이트
    private static final String CURRENT_MUSIC_QUERY =
            "SELECT * FROM MUSIC";
    private static final String TODAY_MUSIC_QUERY =
            "SELECT * FROM today_music";
    private static final String RANK_QUERY =
            "SELECT * FROM (" +
                    "SELECT" +
                    "music_title, " +
                    "music_singer, " +
                    "music_genre, " +
                    "music_like," +
                    "ROW_NUMBER() OVER (PARTITION BY music_genre ORDER BY music_like) AS rank" +
                    "FROM MUSIC ) A";
    private static final String ACCUMULATE =
            "SELECT A.*, B.current_music_number,B.current_music_like,C.accmullike " +
                    "FROM today_music A, " +
                    "(SELECT music_number as current_music_number, music_like as current_music_like FROM music) B, " +
                    "(SELECT music_number,music_like as accmullike FROM accumul) C " +
                    "where A.music_number = B.current_music_number and A.music_number = C.music_number";

    private static final String WEEK_MUSIC_QUERY =
            "SELECT * FROM week_music";

    @Bean
    public Job updateRankJob(Step updateRankStep, Step accumulateStep, Step flatFileStep) {
        return jobBuilderFactory.get("updateRankJob")
                .incrementer(new RunIdIncrementer())
                .start(flatFileStep)
                .build();
    }

    @JobScope
    @Bean
    public Step flatFileStep(FlatFileItemReader<Music> flatFileItemReader,
                             ItemWriter<Music> flatFileWriter
    ) {
        return stepBuilderFactory.get("updateRankStep")
                .<Music, Music>chunk(10)
                .reader(flatFileItemReader)
                .writer(flatFileWriter)
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

    @JobScope
    @Bean
    public Step accumulateStep(JdbcCursorItemReader<AccumulMusic> accumulateReader,
                               ItemProcessor<AccumulMusic, AccumulMusic> accumulateprocessor,
                               ItemWriter<AccumulMusic> accumulateWriter
    ) {
        return stepBuilderFactory.get("accumulateStep")
                .<AccumulMusic, AccumulMusic>chunk(10)
                .reader(accumulateReader)
                .processor(accumulateprocessor)
                .writer(accumulateWriter)
                .build();
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
    public FlatFileItemReader<Music> flatFileItemReader() {
        return new FlatFileItemReaderBuilder<Music>()
                .name("playerFileItemReader")
                .delimited().delimiter(",")
                .names(new String[]{"music_number", "music_title", "music_singer", "music_genre"
                        , "music_release", "music_image", "music_file", "music_lyrics", "music_like"})
                .targetType(Music.class)
                .resource(new FileSystemResource("inputFile.csv"))
                .encoding("MS949")
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
    public ItemWriter<Music> flatFileWriter() {
        return items -> items.forEach(musicService::insertTodayMusic);
    }

    @StepScope
    @Bean
    public ItemWriter<Music> updateRankWriter() {
        return items -> items.forEach(musicService::insertTodayMusic);
    }

    @StepScope
    @Bean
    public ItemWriter<AccumulMusic> accumulateWriter() {
        return items -> items.forEach(musicService::updateAccumulMusic);
    }

}
