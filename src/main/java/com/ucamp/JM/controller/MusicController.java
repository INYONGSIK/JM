package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @RequestMapping("/music")
    public String test(Model model){

        ArrayList<Music> musicList = musicService.selectAllMusic();

 
        model.addAttribute("musicList", musicList);
        return "music";
    }
}
