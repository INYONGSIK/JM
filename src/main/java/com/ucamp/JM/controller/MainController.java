package com.ucamp.JM.controller;

import com.ucamp.JM.config.DailyJobConfig;
import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.AlarmService;
import com.ucamp.JM.service.MusicService;
import com.ucamp.JM.service.main.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    @Autowired
    MainService mainService;
    private final MusicService musicService;
    private final AlarmService alarmService;


    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    DailyJobConfig job;

    @RequestMapping("/jobLauncher")
    public String handle() throws Exception {
        jobLauncher.run(job.dailyjob(), new JobParameters());

        return "redirect:/";
    }

    @RequestMapping("/mainRank")
    @ResponseBody
    public ArrayList<Music> mainRank() {
        ArrayList<Music> musicList = musicService.selectTopMusic();
        return musicList;
    }


    //게시판


    @RequestMapping("/followee")
    @ResponseBody
    public ArrayList<User> followee(@RequestParam int follower) {
        ArrayList<User> followeeList = alarmService.selectFollowee(follower);
        return followeeList;
    }

    @RequestMapping("/deleteFollowee")
    @ResponseBody
    public void deleteFollowee(@RequestParam int follower, @RequestParam int followee) {
        alarmService.deleteFollowee(follower, followee);
        return;
    }

}
