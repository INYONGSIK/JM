package com.ucamp.JM.config;

import com.ucamp.JM.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchSchedular {


    private JobLauncher jobLauncher;
    private DailyJobConfig dailyJobConfig;
    private WeeklyJobConfig weeklyJobConfig;
    private MonthJobConfig monthJobConfig;
    private MusicService musicService;
    JobParameters jobParam = new JobParameters();
    //@Scheduled(cron = "0 0 0 * * *")
    //0초 0분 0시 매일

    JobExecution jobExecution;

    @Scheduled(cron = "0 5 10 * * *")
    public void daily() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        //(현재 좋아요 - today 좋아요) + 누적좋아요 합산 후 누적 테이블에 update
        //today 좋아요에 현재 좋아요 update;
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(dailyJobConfig.dailyjob(), jobParameters);
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus().getBatchStatus())) {
            log.info("completed");
        }
    }

    @Scheduled(cron = "0 10 0 * * MON ")
    public void weekly() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        //0초 10분 0시 매주 월요일
        //week table 에 누적 테이블 값 insert
        //누적 테이블 좋아요 0으로 리셋
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(weeklyJobConfig.weeklyJob(), jobParameters);
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus().getBatchStatus())) {
            musicService.updateAccumulMusicLikeToZero();
        }
    }

    @Scheduled(cron = "0 20 0 1 * *")
    //0초 20분 0시 매달 1일
    public void FirstOfMonth() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        //누적 좋아요 + week 좋아요 합산해서 MONTH에넣기
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(monthJobConfig.updateMonthJob(), jobParameters);
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus().getBatchStatus())) {
            musicService.updateWeekMusicLikeToZero();
        }
    }
}
