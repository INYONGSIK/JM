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
    public String listPM(Model model,HttpServletRequest request){
        String user_email = (String)request.getSession().getAttribute("user_email");
//        System.out.println(playlist_manageService.PMgetUserNumByEmail(user_email).getUser_number());
        int user_number = playlist_manageService.PMgetUserNumByEmail(user_email).getUser_number();
        model.addAttribute("user_number",user_number);

        ArrayList<Playlist_Manage> playlist_manageList = playlist_manageService.selectAllPM(user_number);
        model.addAttribute("PMList", playlist_manageList);
        return "playlist/PMList";
    }



    //플레이리스트 생성폼으로 이동
    @RequestMapping("/addPM")
    public String addPM(HttpServletRequest request,Model model){
        String user_email = (String)request.getSession().getAttribute("user_email");


        model.addAttribute("PM_user_number",playlist_manageService.PMgetUserNumByEmail(user_email).getUser_number());


        return "playlist/addPMform";
    }

    //플레이리스트 생성
    @RequestMapping("/add_PM")
    public String add_PM(HttpServletRequest request,Model model,Playlist_Manage playlist_manage){
        String user_email = (String)request.getSession().getAttribute("user_email");

//        System.out.println(user_email);
//        System.out.println(playlist_manageService.PMgetUserNumByEmail(user_email));
//        model.addAttribute("PM_user_number",playlist_manageService.PMgetUserNumByEmail(user_email));


        playlist_manageService.insertPlaylist_Manage(playlist_manage);

        return "redirect:/listPM";
    }

}
