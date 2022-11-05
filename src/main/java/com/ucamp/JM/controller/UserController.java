package com.ucamp.JM.controller;

import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/asd")
    @ResponseBody
    public String asd() {
        String html = "";
        List<User> users = userService.selectUser();
        System.out.println(users);
        for (User user : users) {
            html += user.toString() + "<br>";
        }
        return html;
    }


}
