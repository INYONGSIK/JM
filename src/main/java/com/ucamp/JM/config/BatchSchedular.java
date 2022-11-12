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
    private Job job;
    private MusicService musicService;
    JobParameters jobParam = new JobParameters();
    //@Scheduled(cron = "0 0 0 * * *")
    //0초 0분 0시 매일

    JobExecution jobExecution;

    @Scheduled(cron = "0 15 20 * * *")
    public void daily() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        //(현재 좋아요 - today 좋아요) + 누적좋아요 합산 후 누적 테이블에 update
        //today 좋아요에 현재 좋아요 update;
        jobLauncher.run(dailyJobConfig.dailyjob(), jobParam);
        log.info("start2::" + jobExecution.toString());
        while (jobExecution.isRunning()) {
            if (log.isDebugEnabled()) {
                log.debug("Run::" + jobExecution.toString());
            }
        }
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus().getBatchStatus())) {
            log.info("completed");
        }
        if (BatchStatus.FAILED.equals(jobExecution.getStatus().getBatchStatus())) {
            log.error("Fail");
        }
        log.info("job");
    }

    @Scheduled(cron = "0 37 18 * * *")
    public void ex() {
        //(현재 좋아요 - today 좋아요) + 누적좋아요 합산 후 누적 테이블에 update
        //today 좋아요에 현재 좋아요 update
        try {
            log.info("job22");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Scheduled(cron = "0 10 0 * * MON ")
    public void weekly() {
        //0초 10분 0시 매주 월요일
        //week table 에 누적 테이블 값 insert
        //누적 테이블 좋아요 0으로 리셋
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus().getBatchStatus())) {
            musicService.updateAccumulMusicLikeToZero();
        }
    }

    @Scheduled(cron = "0 20 0 1 * *")
    //0초 20분 0시 매달 1일
    public void FirstOfMonth() {
        //누적 좋아요 + week 좋아요 합산해서 MONTH에넣기
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus().getBatchStatus())) {
            musicService.updateWeekMusicLikeToZero();
        }
    }
}
