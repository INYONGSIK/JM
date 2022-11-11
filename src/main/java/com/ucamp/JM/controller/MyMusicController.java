package com.ucamp.JM.controller;

import com.ucamp.JM.service.MyMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyMusicController {
    @Autowired
    MyMusicService myMusicService;

    @RequestMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @RequestMapping("/addMyMusic")
    public String addMyMusic() {
        return "addMyMusic";
    }


}
