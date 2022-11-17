package com.ucamp.JM.config;

import com.ucamp.JM.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchSchedular {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    DailyJobConfig dailyJobConfig;
    @Autowired
    WeeklyJobConfig weeklyJobConfig;
    @Autowired
    MonthJobConfig monthJobConfig;
    @Autowired
    MusicService musicService;
    //@Scheduled(cron = "0 0 0 * * *")
    //0초 0분 0시 매일


    JobExecution jobExecution;

    private int dateCount = 1;
    private int weekCount = 1;
    private int monthCount = 1;

    //@Scheduled(cron = "*/30 * * * * *")
    @Scheduled(fixedRate = 31000, initialDelay = 30000)
    public void daily() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        //(현재 좋아요 - today 좋아요) + 누적좋아요 합산 후 누적 테이블에 update
        //today 좋아요에 현재 좋아요 update;
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(dailyJobConfig.dailyjob(), jobParameters);
        System.out.println(dateCount + "일이 지났습니다.");
        dateCount++;
    }


    //@Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(fixedRate = 62000, initialDelay = 62000)
    public void weekly() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, InterruptedException {
        //0초 10분 0시 매주 월요일
        //week table 에 누적 테이블 값 insert
        //누적 테이블 좋아요 0으로 리셋
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(weeklyJobConfig.weeklyJob(), jobParameters);
        musicService.updateAccumulMusicLikeToZero();
        System.out.println(weekCount + "주가 지났습니다.");
        weekCount++;
    }

    //    @Scheduled(cron = "0 59 9 1 * *")
    @Scheduled(fixedRate = 120000, initialDelay = 120000)
    //@Scheduled(cron = "0 0/2 * * * ?")
    public void FirstOfMonth() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, InterruptedException {
        //누적 좋아요 + week 좋아요 합산해서 MONTH에넣기
        musicService.delete_month_music();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(monthJobConfig.updateMonthJob(), jobParameters);
        musicService.delete_week_music();
        System.out.println(monthCount + "달이 지났습니다.");
        monthCount++;
    }
}