package com.ucamp.JM.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class WeeklyJobConfig {
/*    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private final MusicServiceImpl musicService;
    private static final String WEEKLY = "SELECT music_number,music_title,music_singer,music_genre,music_release," +
            "music_image,music_file,music_lyrics,music_like FROM ACCUMUL";

    @Bean
    public Job job1(Step insertWeeklyMusicStep, Step updateTodayMusicStep) {
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(insertWeeklyMusicStep)
                .next(updateTodayMusicStep)
                .build();
    }


    @JobScope
    @Bean
    public Step insertWeeklyMusicStep(JdbcCursorItemReader<Music> insertWeeklyMusicReader,
                                      ItemWriter<Music> insertWeeklyMusicWriter
    ) {
        return stepBuilderFactory.get("updateTodayMusicStep")
                .<Music, Music>chunk(10)
                .reader(insertWeeklyMusicReader)
                .writer(insertWeeklyMusicWriter)
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
    public ItemWriter<Music> updateWeeklyMusicWriter() {
        return items -> items.forEach(musicService::insertWeekMusic);
    }
*/

}
