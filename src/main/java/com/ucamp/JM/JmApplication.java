package com.ucamp.JM;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing()
@SpringBootApplication
@EnableScheduling
public class JmApplication {

    static JobParameters jobParameters;
    static JobLauncher jobLauncher;
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(JmApplication.class, args);
        System.out.println("&&&&&&&&&&&&&&&&");
    }

}
