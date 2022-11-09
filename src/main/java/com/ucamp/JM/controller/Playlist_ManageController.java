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

    @RequestMapping("/listPM")
    public String listPM(Model model){
        ArrayList<Playlist_Manage> playlist_manageList = playlist_manageService.selectAllPM();

        model.addAttribute("PMList", playlist_manageList);
        return "PMList";
    }

    @RequestMapping("/addPM")
    public String addPM(HttpServletRequest request){
        return "redirect:/addplaylistform";
    }

    @RequestMapping("/add")
    public String add(Playlist_Manage playlist_manage){
        playlist_manageService.insertPlaylist_Manage(playlist_manage);
        return "redirect:/";
    }

}
