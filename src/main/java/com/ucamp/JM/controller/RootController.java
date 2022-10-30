package com.ucamp.JM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//test
@Controller
@RequestMapping("/")
public class RootController {
    @GetMapping("/")
    public String main(){
        return "main";
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
