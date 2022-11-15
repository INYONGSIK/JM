package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.service.GetWeekOf;
import com.ucamp.JM.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MusicController {

    private final MusicService musicService;
    private final GetWeekOf getWeekOf;

    @RequestMapping("/uploadMyMusic")
    public String uploadMyMusic() {
        Music music = new Music();
        music.setMusic_number(502);
        music.setMusic_title("uploadtitle");
        music.setMusic_singer("uploadSinger");
        music.setMusic_genre("트로트");
        music.setMusic_release("2020-10-10");
        music.setMusic_image("imgimg");
        music.setMusic_file("filefile");
        music.setMusic_lyrics("아무가사");
        music.setMusic_like(100);
        musicService.insertWeekMusic(music);
        return "main";
    }

    @RequestMapping("/uploadMyMusicForm")
    public String uploadMyMusicForm() {
        return "uploadMyMusicForm";
    }

    @RequestMapping("/rank")
    public String music(@RequestParam(defaultValue = "music") String dateValue, @RequestParam(defaultValue = "전체") String genre, Model model) {
        return "rank";
    }

    @RequestMapping("/rank2")
    @ResponseBody
    public ArrayList<Music> music2(@RequestParam Map<String, String> param) {
        ArrayList<Music> musicList = new ArrayList<>();
        String dateValue = param.get("dateValue");
        String genre = param.get("genre");
        if (dateValue.equals("music") && genre.equals("전체")) {
            musicList = musicService.selectTopMusic();
        } else if (dateValue.equals("music") && !genre.equals("전체")) {
            musicList = musicService.selectTopMusicByGenre(genre);
        } else if (!dateValue.equals("music") && genre.equals("전체")) {
            musicList = musicService.selectTopMusicByDate(dateValue);
        } else {
            musicList = musicService.selectTopMusicByDateAndGenre(dateValue, genre);
        }
        return musicList;

    }

    @RequestMapping("/rank3")
    @ResponseBody
    public String music3(@RequestParam(defaultValue = "music") String dateValue) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String[] date = sdf.format(now).split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        int week = getWeekOf.getCurrentWeekOfMonth(year, month, day);
        if (dateValue.equals("month_music")) {
            result = year + "년" + month + "월";
        } else if (dateValue.equals("week_music")) {
            result = month + "월" + week + "주";
        } else if (dateValue.equals("today_music")) {
            result = month + "월" + day + "일";
        } else {
            result = month + "월" + day + "일 실시간";
        }
        return result;
    }


    //현호 == 음악 검색시 검색창이 비어있으면 전체리스트 출시일 기준으로 출력,
    //      검색 시 키워드 포함한 모든 컬럼과 대조해서 값 가져오기
    @RequestMapping("/musicSearch")
    public String musicSearch(@RequestParam String keyword, @RequestParam String genre, Model model) {
        model.addAttribute("musics", musicService.SearchByKeyword("%" + keyword + "%", "%" + genre + "%"));
        return "musicSearch";
    }

    @RequestMapping("/musicDetails/{music_number}")
    public String read(Model model, @PathVariable int music_number) {
        model.addAttribute("details", musicService.showMusicDetails(music_number));
        return "musicDetails";
    }

    @RequestMapping("/likeIncrement/{music_number}")
    public String like(Model model, @PathVariable int music_number) {
        musicService.likeIncrement(music_number);
        return "redirect:/musicDetails/" + music_number;
    }

}

