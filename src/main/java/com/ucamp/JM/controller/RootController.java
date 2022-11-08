package com.ucamp.JM.controller;

import com.ucamp.JM.service.MusicServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class RootController {
    private final MusicServiceImpl musicService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/rank")
    public String rank(Model model) {
        model.addAttribute("ranklist", musicService.selectRankMusic());
        return "rank";
    }

//    @GetMapping("/loginForm")
//    public String loginForm(){
//        return "loginForm";
//    }
//    @GetMapping("/joinForm")
//    public String joinForm(){
//        return "joinForm";
//    }

}
