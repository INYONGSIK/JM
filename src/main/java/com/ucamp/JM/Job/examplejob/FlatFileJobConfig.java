package com.ucamp.JM.Job.examplejob;

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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@AllArgsConstructor
@Slf4j
public class FlatFileJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final musicInitService musicInitService;

    @Bean
    public Job flatFileJob(Step flatFileStep) {
        return jobBuilderFactory.get("flatFileJob")
                .incrementer(new RunIdIncrementer())
                .start(flatFileStep)
                .build();
    }

    @JobScope
    @Bean
    public Step flatFileStep(FlatFileItemReader<musicDto> playerFileItemReader,
                             ItemWriter<musicDto> playerFileItemWriter
    ) {
        return stepBuilderFactory.get("flatFileStep")
                .<musicDto, musicDto>chunk(10)
                .reader(playerFileItemReader)
                .writer(playerFileItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<musicDto> playerFileItemWriter() {
        return items -> items.forEach(musicInitService::musicInit);
    }

    @StepScope
    @Bean
    public FlatFileItemReader<musicDto> playerFileItemReader() {
        return new FlatFileItemReaderBuilder<musicDto>()
                .name("playerFileItemReader")
                .delimited()
                .delimiter(",")
                .names("music_number", "music_title", "music_singer", "music_genre", "music_release", "music_image", "music_file", "music_lyrics", "music_like")
                //.fieldSetMapper(new PlayerFieldSetMapper())
                .encoding("MS949")
                .targetType(musicDto.class)
                .resource(new FileSystemResource("inputFile.csv"))
                .build();
    }

}
