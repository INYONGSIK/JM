package com.ucamp.JM.controller;

import com.ucamp.JM.dao.UserDAO;
import com.ucamp.JM.dto.Like;
import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.*;
import com.ucamp.JM.service.board.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    MusicService musicService;

    @Autowired
    BoardService boardService;

    @Autowired
    PlaylistService playlistService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private HttpSession session;
    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @Autowired
    UserDAO userDAO;

    private Logger logger = LoggerFactory.getLogger(RootController.class);

    @RequestMapping("/mypage")
    public String mypage(Model model) throws Exception {
        String email = (String) session.getAttribute("user_email");
        User user = userService.queryUser(email);

        ArrayList<Music> uploadMusicList = myMusicService.getMusicByMusicSinger(user.getUser_name());

        ArrayList<Music> upload = new ArrayList<>();
        for (int i = 0; i < uploadMusicList.size(); i++) {
            upload.add(uploadMusicList.get(i));
            upload.get(i).setMusic_release(uploadMusicList.get(i).getMusic_release().substring(0, 10));
        }


        ArrayList<Like> likeList = musicService.LikeSelectAll(user.getUser_number());
        Music like_music1 = null;
        ArrayList<Music> like_musicList = new ArrayList<>();
        for (int i = 0; i < likeList.size(); ++i) {
            int likeList_Music_num = likeList.get(i).getMusic_number();
            like_music1 = myMusicService.myLikeMusicList(likeList_Music_num);
            like_musicList.add(like_music1);
        }
        ArrayList<Music> setLike = new ArrayList<>();
        for (int i = 0; i < like_musicList.size(); i++) {
            setLike.add(like_musicList.get(i));
            setLike.get(i).setMusic_release(like_musicList.get(i).getMusic_release().substring(0, 10));
        }


        model.addAttribute("likeMusicList", setLike);

        //좋아요
        //MyMusic likeParamDto = new MyMusic();
        //likeParamDto.setUser_number(String.valueOf(user.getUser_number()));
        // likeParamDto.setList_name("like");
        // ArrayList<MyMusic> likeMusicList = myMusicService.myLikeMusicList(likeParamDto);

        model.addAttribute("uploadMusicList", upload);
        // model.addAttribute("likeMusicList", likeMusicList);
        return "mypage";
    }


    @RequestMapping("/delMyMusic/{music_number}")
    public String delMyMusic(HttpServletRequest request, @PathVariable int music_number) throws Exception {
        String email = (String) session.getAttribute("user_email");
        String user_nickname = boardService.getUserNicknameByEmail(email).getUser_nickname();
        int user_number = boardService.getUserNumByNickname(user_nickname).getUser_number();


        musicService.deleteLike2(music_number);

        adminService.deleteReportMusic(music_number);

        myMusicService.delMyMusic(music_number);


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
        String lyrics = request.getParameter("music_lyrics");
        if (request.getParameter("music_title") == null) {
            String errormsg = "제목 미입력";
            return "redirect:/mypage";
        }
        if (lyrics == null) {
            lyrics = "가사 없음";
        }
        Music music = new Music();

        music.setMusic_title(request.getParameter("music_title"));
        music.setMusic_singer(request.getParameter("music_singer"));
        music.setMusic_genre(request.getParameter("music_genre"));
        music.setMusic_release(request.getParameter("music_release"));
        music.setMusic_lyrics(lyrics);

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

            myMusicService.insertMyMusicToday(music);
            myMusicService.insertMyMusicAccumul(music);


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


