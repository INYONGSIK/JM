package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MusicController {

    private final MusicService musicService;

    ArrayList<Music> musicList = null;

    @RequestMapping("/rank")
    public String music(@RequestParam(value = "date", defaultValue = "music") String dateValue, @RequestParam(value = "genre", defaultValue = "전체") String genre, Model model) {
        /*if (date.equals("music") && genre.equals("전체")) {
            musicList = musicService.selectTopMusic();
        } else if (date.equals("music") && !genre.equals("전체")) {
            musicList = musicService.selectTopMusicByGenre(genre);
        } else if (!date.equals("music") && genre.equals("전체")) {
            musicList = musicService.selectTopMusicByDate(date);
        } else {
            musicList = musicService.selectTopMusicByDateAndGenre(date, genre);
        }*/
        log.info("dataValue:" + dateValue);
        musicList = musicService.selectTopMusicByDate(dateValue);
        model.addAttribute("musicList", musicList);
        return "rank";
        

    //현호 == 음악 검색시 검색창이 비어있으면 전체리스트 출시일 기준으로 출력,
    //      검색 시 키워드 포함한 모든 컬럼과 대조해서 값 가져오기
    @RequestMapping("/musicSearch")
    public String musicSearch(@RequestParam String keyword, @RequestParam String genre, Model model) {

        model.addAttribute("musics", musicService.SearchByKeyword("%" + keyword + "%", "%" + genre + "%"));

        return "musicSearch";

    }
}



