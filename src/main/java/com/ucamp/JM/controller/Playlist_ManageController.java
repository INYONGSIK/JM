package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.service.Playlist_ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class Playlist_ManageController {


    private final Playlist_ManageService playlist_manageService;

    //플레이리스트를 보여줍니다
    @RequestMapping("/listPM")
    public String listPM(Model model){
        ArrayList<Playlist_Manage> playlist_manageList = playlist_manageService.selectAllPM();
        model.addAttribute("PMList", playlist_manageList);
        return "/playlist/PMList";
    }

    //플레이리스트 생성폼으로 이동
    @RequestMapping("/addPM")
    public String addPM(HttpServletRequest request, Model model){
        String user_email = (String)request.getSession().getAttribute("user_email");
        model.addAttribute("PM_user_number",    playlist_manageService.PMgetUserNumByEmail(user_email).getUser_number());
        return "/playlist/addPMform";
    }

    //플레이리스트 생성
    @RequestMapping("/add_PM")
    public String add_PM(Playlist_Manage playlist_manage){
        playlist_manageService.insertPlaylist_Manage(playlist_manage);
        return "redirect:/listPM";
    }

}
