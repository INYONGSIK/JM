package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @RequestMapping("/music")
    public String test(Model model) {

        ArrayList<Music> musicList = musicService.selectAllMusic();

<<<<<<< HEAD
=======
        ArrayList<Music> musicList = musicService.selectAllMusic();
>>>>>>> f2426c85a1eb56296f654f2cca2f46ce5e97fcfd

        model.addAttribute("musicList", musicList);
        return "music";
    }
}
