package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.AlarmService;
import com.ucamp.JM.service.MusicService;
import com.ucamp.JM.service.board.BoardService;
import com.ucamp.JM.service.main.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/mainRank")
    @ResponseBody
    public ArrayList<Music> mainRank() {
        ArrayList<Music> musicList = musicService.selectTopMusic();
        return musicList;
    }


    @RequestMapping("/main")
    public  String main(Model model) {
        model.addAttribute("dashboardstop10", mainService.mainBoardSelectTop10());
        return "main/index";

    }

    @RequestMapping("/main2")
    public  String main2(Model model) {
        model.addAttribute("dashboardstop10", mainService.mainBoardSelectTop10());
        return "main/index2";

    }



    //게시판


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
