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

        model.addAttribute("musicList", musicList);
        return "music";
    }

    //현호 == 음악 검색시 검색창이 비어있으면 전체리스트 출시일 기준으로 출력,
    //      검색 시 키워드 포함한 모든 컬럼과 대조해서 값 가져오기
    @RequestMapping("/musicSearch")
    public String musicSearch(String keyword, Model model) {

        ArrayList<Music> musicList = musicService.selectAllMusic();

        model.addAttribute("musicList", musicList);

        if (keyword == null)
            model.addAttribute("musics", musicService.selectAllMusic());
        else
            model.addAttribute("musics", musicService.selectKeyword("%" + keyword + "%"));

        return "musicSearch";
    }
}



