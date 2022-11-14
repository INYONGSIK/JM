package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.AlarmService;
import com.ucamp.JM.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MusicService musicService;
    private final AlarmService alarmService;

    @RequestMapping("/mainRank")
    @ResponseBody
    public ArrayList<Music> mainRank() {
        ArrayList<Music> musicList = musicService.selectTopMusic();
        return musicList;
    }

    @RequestMapping("/followee")
    @ResponseBody
    public ArrayList<User> followee() {
        ArrayList<User> followeeList = alarmService.selectFollowee(1);
        log.info(String.valueOf(followeeList.size()));
        return followeeList;
    }

    @RequestMapping("/deleteFollowee")
    @ResponseBody
    public void foldeleteFolloweelower() {
        int follower1 = 1;
        int followee2 = 11;

        alarmService.deleteFollowee(follower1, followee2);
        return;
    }
}
