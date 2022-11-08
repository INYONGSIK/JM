package com.ucamp.JM.controller;

import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class RootController {

    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/registerform")
    public String registerform() {
        return "registerform";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        try {
            user.setType("user");
            userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "main";
    }


}
