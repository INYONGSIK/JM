package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.MyMusic;
import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.MyMusicService;
import com.ucamp.JM.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class MyMusicController {
    @Autowired
    MyMusicService myMusicService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private HttpSession session;
    @Autowired
    UserService userService;

    private Logger logger = LoggerFactory.getLogger(RootController.class);

    @RequestMapping("/mypage")
    public String mypage(Model model) throws Exception {
        String email = (String) session.getAttribute("user_email");
        User user = userService.queryUser(email);

        ArrayList<Music> music = myMusicService.getMusicByMusicSinger(user.getUser_name());

        model.addAttribute("musicList", music);
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

    @ResponseBody
    @PostMapping("/delMyMusic")
    public String delMyMusic(@RequestParam String list_name, @RequestParam String playlist_cd, @RequestParam String music_number) throws Exception {
        //String result = "실패";

        //파일삭제 추후구현

        //파일삭제 성공하면 db삭제

        //db삭제 성공
        //int i = myMusicService.delMyMusic(dto);

        // if (i > 0) {
        //     result = "성공";
        // }

        // return result;
        // myMusicService.delMyMusic();
        return "redirect:/mypage";
    }

    @RequestMapping("/addMyMusic")
    public String addMyMusic(Model model) throws Exception {
        String email = (String) session.getAttribute("user_email");
        User user = userService.queryUser(email);
        model.addAttribute("music_singer", user.getUser_name());
        return "addMyMusic";
    }

    @RequestMapping("/add")
    public String add(HttpServletRequest request, MultipartFile music_image, MultipartFile music_file) {
        Music music = new Music();
        music.setMusic_title(request.getParameter("music_title"));
        music.setMusic_singer(request.getParameter("music_singer"));
        music.setMusic_genre(request.getParameter("music_genre"));
        music.setMusic_release(request.getParameter("music_release"));
        music.setMusic_lyrics(request.getParameter("music_lyrics"));

        try {
            if (!music_image.getOriginalFilename().equals("")) {
                // 원래 파일 이름
                String profile = music_image.getOriginalFilename();
                // 파일이름으로 사용할 uuid 생성
                String uuid = UUID.randomUUID().toString();
                // 확장자 추출 ( ex.png)
                String extension = profile.substring(profile.lastIndexOf(".") + 1);
                // uuid 와 확장자 결합
                String saveProfile = uuid + "." + extension;
                // 저장할 위치
                // servletContext 쓸려면 private final ServletContext servletContext; 해줘여함
                String path = servletContext.getRealPath("/img/musicimage/");
                System.out.println("path  " + path);
                File destFile = new File(path + saveProfile);
                music_image.transferTo(destFile);
                music.setMusic_image(saveProfile.toString());

            } else {
                music.setMusic_image("basic.png");
            }
            // 음악 파일
            if (!music_file.getOriginalFilename().equals("")) {
                // 원래 파일 이름
                String profile = music_file.getOriginalFilename();
                // 파일이름으로 사용할 uuid 생성
                String uuid = UUID.randomUUID().toString();
                // 확장자 추출 ( ex.png)
                String extension = profile.substring(profile.lastIndexOf(".") + 1);
                // uuid 와 확장자 결합
                String saveProfile = uuid + "." + extension;
                // 저장할 위치
                // servletContext 쓸려면 private final ServletContext servletContext; 해줘여함
                String path = servletContext.getRealPath("/music/");
                System.out.println("path2  " + path);
                File destFile = new File(path + saveProfile);
                music_file.transferTo(destFile);
                music.setMusic_file(saveProfile.toString());
            }
            myMusicService.insertMyMusic(music);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/mypage";
    }
}
