package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.MyMusicService;
import com.ucamp.JM.service.PlaylistService;
import com.ucamp.JM.service.UserService;
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
    PlaylistService playlistService;
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

        ArrayList<Music> uploadMusicList = myMusicService.getMusicByMusicSinger(user.getUser_name());

        //좋아요
        //MyMusic likeParamDto = new MyMusic();
        //likeParamDto.setUser_number(String.valueOf(user.getUser_number()));
        // likeParamDto.setList_name("like");
        // ArrayList<MyMusic> likeMusicList = myMusicService.myLikeMusicList(likeParamDto);

        model.addAttribute("uploadMusicList", uploadMusicList);
        // model.addAttribute("likeMusicList", likeMusicList);
        return "mypage";
    }

    @ResponseBody
    @PostMapping("/delMyMusic")
    public String delMyMusic(@RequestParam String list_name, @RequestParam String playlist_cd, @RequestParam String music_number) throws Exception {
        //실제파일삭제 구현
        myMusicService.delMyMusic(Integer.parseInt(music_number));
        playlistService.deletePlaylistMusic(Integer.parseInt(music_number));

        //db삭제
        //MUSIC
        //PLAYLIST

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
    public String add(HttpServletRequest request, MultipartFile music_image, MultipartFile music_file) throws Exception {
        Music music = new Music();
        music.setMusic_title(request.getParameter("music_title"));
        music.setMusic_singer(request.getParameter("music_singer"));
        music.setMusic_genre(request.getParameter("music_genre"));
        music.setMusic_release(request.getParameter("music_release"));
        music.setMusic_lyrics(request.getParameter("music_lyrics"));

        String email = (String) session.getAttribute("user_email");
        User user = userService.queryUser(email);

        //전역변수 뮤직넘버

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

            //int musicNumber = myMusicService.maxMusicNumber();

            ////업로드 플레이리스트 등록
            // Playlist playlist = new Playlist();
            // playlist.setMusic_number(musicNumber);
            //playlist.setList_name("upload");
            //playlist.setUser_number(user.getUser_number());
            //playlistService.insertPlaylist(playlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/mypage";
    }

}

