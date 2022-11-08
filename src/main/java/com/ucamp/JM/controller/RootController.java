package com.ucamp.JM.controller;

import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class RootController {

    private final UserService userService;
    private final HttpSession session;

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
        return "redirect:/";
    }

    @GetMapping("/loginform")
    public String loginform() {
        return "loginform";
    }

    @PostMapping("login")
    public String login(@RequestParam String user_email, @RequestParam String user_password, Model model) {
        try {
            userService.login(user_email, user_password);
            session.setAttribute("user_email", user_email);
            return "redirect:/";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

}

