//package com.ucamp.JM.config;
//​
//import com.ucamp.JM.dto.AccumulMusic;
//import com.ucamp.JM.dto.Music;
//import com.ucamp.JM.service.MusicServiceImpl;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class UpdateRankJobConfig {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    @Autowired
//    private DataSource dataSource;
//    private final MusicServiceImpl musicService;
//
//
//    @Bean
//    public Job updateRankJob(Step flatFileStep) {
//        return jobBuilderFactory.get("updateRankJob")
//                .incrementer(new RunIdIncrementer())
//                .start(flatFileStep)
//                .build();
//    }
//
//    @JobScope
//    @Bean
//    public Step flatFileStep(FlatFileItemReader<Music> flatFileItemReader,
//                             ItemWriter<Music> flatFileWriter
//    ) {
//        return stepBuilderFactory.get("updateRankStep")
//                .<Music, Music>chunk(10)
//                .reader(flatFileItemReader)
//                .writer(flatFileWriter)
//                .build();
//    }
//
//    @StepScope
//    @Bean
//    public FlatFileItemReader<Music> flatFileItemReader() {
//        return new FlatFileItemReaderBuilder<Music>()
//                .name("playerFileItemReader")
//                .delimited().delimiter(",")
//                .names(new String[]{"music_number", "music_title", "music_singer", "music_genre"
//                        , "music_release", "music_image", "music_file", "music_lyrics", "music_like"})
//                .targetType(Music.class)
//                .resource(new FileSystemResource("inputFile.csv"))
//                .encoding("MS949")
//                .build();
//    }
//}
//    @StepScope
//    @Bean
//    public ItemWriter<Music> flatFileWriter() {
//        return items -> items.forEach(musicService::insertTodayMusic);
//    }
//
// }