package com.ucamp.JM.controller;

import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.AdminService;
import com.ucamp.JM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;
    Autowired
    UserService userService;
    @RequestMapping("/admin/userList")
    public String adminUserList(HttpServletRequest request, Model model) throws Exception {
        String email= (String) request.getSession().getAttribute("user_email");
        User user = userService.queryUser(email);
        if (email == null) {
            //loginform 으로 이동
            return "<script>location.href='/loginform';</script>";
        }

        if (!user.getType().equals("admin")) {
            return "<script>alert('관리자만 접속 가능합니다.'); location.href='/loginform';</script>";
        }else{
            model.addAttribute("Users", adminService.selectAllUser());
        }



        return "/admin/userList";
    }
}
