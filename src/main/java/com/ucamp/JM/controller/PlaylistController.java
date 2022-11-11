package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Playlist;
import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.MusicService;
import com.ucamp.JM.service.PlaylistService;
import com.ucamp.JM.service.Playlist_ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private MusicService musicService;

    //플레이리스트의 노래들을 보여줍니다
    @RequestMapping("/listP/{user_number}/{list_name}")
    public String viewPlaylist(HttpServletRequest request, Model model, @PathVariable int user_number, @PathVariable String list_name){

        model.addAttribute("P_user_number",user_number);
        model.addAttribute("P_list_name", list_name);
        ArrayList<Playlist> PlaylistList = playlistService.selectPlaylist(list_name,user_number);
        model.addAttribute("PList",PlaylistList);
        return "playlist/PList";
    }

    // 플레이리스트의 폼으로 이동합니다

    @RequestMapping("/addP")
    public String addP(HttpServletRequest request, Model model){
        String user_email = (String)request.getSession().getAttribute("user_email");
        System.out.println(user_email);

        System.out.println(playlistService.PgetUserNumByEmail(user_email).getUser_number());
        int userNumber = playlistService.PgetUserNumByEmail(user_email).getUser_number();
        List<String> list = playlistService.PgetListNameByUserNum(userNumber);
        System.out.println(list.get(0));
//        System.out.println("playlist" + playlistService);
//        User user = playlistService.PgetUserNumByEmail(user_email);
//        System.out.println(user);
        model.addAttribute("P_user_number", playlistService.PgetUserNumByEmail(user_email).getUser_number());
        model.addAttribute("P_list_name", playlistService.PgetListNameByUserNum(playlistService.PgetUserNumByEmail(user_email).getUser_number()).get(0));
        model.addAttribute("musicList", musicService.selectAllMusic());
        return "playlist/addPform";
    }

    //플레이리스트에 노래를 넣습니다
    @RequestMapping("add_P")
    public String add_P(Playlist playlist){
        playlistService.insertPlaylist(playlist);
        return "redirect:/listP/{user_number}/{list_name}";
    }


}