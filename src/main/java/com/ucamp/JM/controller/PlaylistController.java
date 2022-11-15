package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.Playlist;
import com.ucamp.JM.service.MusicService;
import com.ucamp.JM.service.PlaylistService;
import com.ucamp.JM.service.Playlist_ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private MusicService musicService;

    @Autowired
    private Playlist_ManageService playlistManageService;

    //플레이리스트의 노래들을 보여줍니다
    @RequestMapping("/listP/{user_number}/{list_name}")
    public String viewPlaylist(HttpServletRequest request, Model model, @PathVariable int user_number, @PathVariable String list_name) {

        model.addAttribute("P_user_number", user_number);
        model.addAttribute("P_list_name", list_name);


        ArrayList<Playlist> PlaylistList = playlistService.selectPlaylist(user_number, list_name);

        ArrayList<Music> PlayList1 = new ArrayList<>();
        for (int i = 0; i < PlaylistList.size(); ++i) {
//            PlayList1.add(PlaylistList.get(i));
            PlayList1.add(playlistService.selectByMusicNumber(PlaylistList.get(i).getMusic_number()));

        }
        model.addAttribute("PList", PlayList1);


        return "playlist/PList";
    }

    // 플레이리스트의 폼으로 이동합니다

    @RequestMapping("/addP/{list_name}")
    public String addP(HttpServletRequest request, Model model, @PathVariable String list_name) {
        String user_email = (String) request.getSession().getAttribute("user_email");
//        System.out.println(user_email);
//
//        System.out.println(playlistService.PgetUserNumByEmail(user_email).getUser_number());
        int userNumber = playlistService.PgetUserNumByEmail(user_email).getUser_number();
//        List<String> list = playlistService.PgetListNameByUserNum(userNumber);
////        System.out.println(list.get(0));
////        System.out.println("playlist" + playlistService);
////        User user = playlistService.PgetUserNumByEmail(user_email);
////        System.out.println(user);
        model.addAttribute("P_user_number", playlistService.PgetUserNumByEmail(user_email).getUser_number());
        model.addAttribute("P_list_name", list_name);
        model.addAttribute("musicList", playlistManageService.selectAllMusic2());
        return "playlist/addPform";
    }

    //플레이리스트에 노래를 넣습니다
    //http://localhost:8090/add_P?user_number=3&list_name=kkk&music_number=1
    @RequestMapping("add_P")
    public String add_P(HttpServletResponse response, Playlist playlist, @RequestParam int user_number, @RequestParam String list_name, @RequestParam int music_number) throws IOException {
        System.out.println(user_number + " " + list_name + "" + music_number);

        if(playlistService.selectSameMusic(user_number, list_name, music_number) == null){
            playlistService.insertPlaylist(playlist);
            String encodedParam = null;
                encodedParam = URLEncoder.encode(list_name, "UTF-8");

//        System.out.println("hi"+user_number);
//        return "playlist/PList";
            return "redirect:/listP/" + user_number + "/" + encodedParam;
        } else {
            String encodedParam = null;
            encodedParam = URLEncoder.encode(list_name, "UTF-8");
            Writer out = response.getWriter();
            String message = URLEncoder.encode("같은 노래를 넣을 수 없습니다..", "UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            out.write("<script type=\"text/javascript\">alert(decodeURIComponent('" + message + "'.replace(/\\+/g, '%20'))); location.href='/listP/" + user_number + "/" + encodedParam+ "' </script>");
            out.flush();
            response.flushBuffer();
            out.close();
            return "playlist/PList";
        }


    }


    //플레이리스트에 음악을 삭제합니다
    @RequestMapping("/deletemusic/{music_number}/{list_name}/{user_number}")
    public String deletemusic(@PathVariable int user_number, @PathVariable String list_name, @PathVariable int music_number) throws UnsupportedEncodingException {

        playlistService.deletePlaylistMusic(music_number);
        String encodedParam = null;
        encodedParam = URLEncoder.encode(list_name, "UTF-8");
        return "redirect:/listP/" + user_number + "/" + encodedParam;
    }

}