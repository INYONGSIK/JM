package com.ucamp.JM.Job;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
@AllArgsConstructor
public class updateTopConfig {

        private final JobBuilderFactory jobBuilderFactory;
        private final StepBuilderFactory stepBuilderFactory;
        private final DataSource dataSource;
        private static final int chunkSize = 10;
        @Bean
        public Job updateRankJob(Step updateRankStep) {
            return jobBuilderFactory.get("updateRankJob")
                    .incrementer(new RunIdIncrementer())
                    .start(updateRankStep)
                    .build();
        }
        @JobScope
        @Bean("updateRankStep")
        public Step updateRankStep(ItemReader musicItemReader, ItemWriter musicItemWriter) {
            return stepBuilderFactory.get("updateRankStep")
                    .<String, String> chunk(chunkSize)
                    .reader(musicItemReader)
                    .writer(musicItemWriter)
                    .build();
        }
        @StepScope
        @Bean("musicItemReader")
        public JdbcCursorItemReader<Music> musicItemReader() {
            return new JdbcCursorItemReaderBuilder<Music>()
                    .fetchSize(chunkSize)
                    .dataSource(dataSource)
                    .rowMapper(new BeanPropertyRowMapper<>(Music.class))
                    .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
                    .name("jdbcCursorItemReader")
                    .build();
        }
        @StepScope
        @Bean
        public ItemWriter<Music> musicItemWriter() {
                return list -> {
                        for (Music music: list) {
                                log.info("Current Pay= {}", pay);
                        }
                };
        }
}
