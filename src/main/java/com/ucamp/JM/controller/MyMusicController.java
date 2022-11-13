package com.ucamp.JM.controller;

import com.ucamp.JM.dto.MyMusic;
import com.ucamp.JM.service.MyMusicService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class MyMusicController {
    @Autowired
    MyMusicService myMusicService;

    private HttpSession session;

    private Logger logger = LoggerFactory.getLogger(RootController.class);

    @RequestMapping("/mypage")
    public String mypage() {

        return "mypage";
    }

    @ResponseBody
    @PostMapping("/myMusicList")
    public String myMusicList(HttpServletRequest request, @RequestParam String list_name) throws Exception {
        String user_number = String.valueOf(request.getSession().getAttribute("user_number"));

        MyMusic paramDto = new MyMusic();
        paramDto.setUser_number(user_number);
        paramDto.setList_name(list_name);

        ArrayList<MyMusic> myMusicList = myMusicService.myMusicList(paramDto);

        JSONObject resJsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < myMusicList.size(); i++) {
            JSONObject paramJSONObject = new JSONObject();
            paramJSONObject.put("list_name", myMusicList.get(i).getList_name());
            paramJSONObject.put("playlist_cd", myMusicList.get(i).getPlaylist_cd());
            paramJSONObject.put("music_number", myMusicList.get(i).getMusic_number());
            paramJSONObject.put("music_title", myMusicList.get(i).getMusic_title());
            paramJSONObject.put("music_singer", myMusicList.get(i).getMusic_singer());
            paramJSONObject.put("music_genre", myMusicList.get(i).getMusic_genre());
            jsonArray.add(paramJSONObject);
        }

        resJsonObject.put("myMusicList", jsonArray);
        String jsonString = resJsonObject.toJSONString();

        return jsonString;
    }

//    @ResponseBody
//    @PostMapping("/delMyMusic")
//    public String delMyMusic(@RequestParam String list_name, @RequestParam String playlist_cd, @RequestParam String music_number) throws Exception {
//        String result = "실패";
//
//        //파일삭제 추후구현
//
//        //파일삭제 성공하면 db삭제
//
//        //db삭제 성공
//        int i = myMusicService.delMyMusic(dto);
//
//        if (i > 0) {
//            result = "성공";
//        }
//
//        return result;
//    }


    @RequestMapping("/addMyMusic")
    public String addMyMusic() {

        return "addMyMusic";
    }

    @RequestMapping("/add")
    public String add(HttpServletRequest request, MyMusic myMusic) {
        myMusicService.insertMyMusic(myMusic);
        return "redirect:/mypage";
    }
}
