package com.ucamp.JM.controller;

import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.AdminService;
import com.ucamp.JM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;


    @RequestMapping("/admin")
    public String admin(HttpServletRequest request) throws Exception {
        String email = (String) request.getSession().getAttribute("user_email");
        User user = null;
        if (email == null) {
            //loginform 으로 이동
            return "loginform";
        } else {
            user = userService.queryUser(email);
        }

        if (!user.getType().equals("admin")) {
            return "redirect:/";
        }

        return "/admin/adminHome";

    }

    @RequestMapping("/admin/report/{type}")
    public String adminReport(HttpServletRequest request, Model model, @PathVariable int type) throws Exception {

        String email = (String) request.getSession().getAttribute("user_email");
        User user = null;
        if (email == null) {
            //loginform 으로 이동
            return "loginform";
        } else {
            user = userService.queryUser(email);
        }

        if (!user.getType().equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("reports", adminService.selectReportByType(type));
        return "/admin/report";
    }


    @RequestMapping("/admin/userList")
    public String adminUserList(HttpServletRequest request, Model model) throws Exception {

        String email = (String) request.getSession().getAttribute("user_email");
        User user = null;

        if (email == null) {
            //loginform 으로 이동
            return "loginform";
        } else {
            user = userService.queryUser(email);
        }

        if (!user.getType().equals("admin")) {

            return "redirect:/";
        }

        model.addAttribute("users", adminService.selectAllUser());
        return "/admin/userList";
    }

    @RequestMapping("/admin/deleteUser/{user_number}")
    public String deleteUser(@PathVariable int user_number) {
        adminService.deleteUser(user_number);

        return "redirect:/admin/userList";
    }

}
