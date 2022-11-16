package com.ucamp.JM.controller;

import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.AdminService;
import com.ucamp.JM.service.MusicService;
import com.ucamp.JM.service.UserService;
import com.ucamp.JM.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;

    @Autowired
    MusicService musicService;


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


    @RequestMapping("/admin/reportedUserList")
    public String reportedUserList(HttpServletRequest request, Model model) throws Exception {

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

        model.addAttribute("users", adminService.selectAllUserByReport());

        return "/admin/reportedUserList";
    }


    @RequestMapping("/admin/deleteUser/{user_number}")
    public String deleteUser(@PathVariable int user_number) {
        adminService.deleteUser(user_number);

        return "redirect:/admin/userList";
    }

    @RequestMapping("/admin/deleteReport/{report_type}/{report_ID}")
    public String deleteReport(@PathVariable int report_type, @PathVariable int report_ID) {
        adminService.deleteReport(report_ID);

        return "redirect:/admin/report/" + report_type;
    }


    @RequestMapping("/admin/adminUserList")
    public String adminUserList(Model model) {

        model.addAttribute("users", adminService.selectAllUser());
        return "/admin/adminUserList";
    }

    @GetMapping("/reportMusic/{music_singer}/{music_title}/{music_number}")
    public String reportMusic(HttpServletResponse response, @PathVariable String music_singer, @PathVariable String music_title, @PathVariable int music_number) throws IOException {

        System.out.println("musicSinger" + music_singer);

        int user_number = boardService.getUserNumByname(music_singer).getUser_number();



        if (boardService.selectOk(user_number, music_title) != null) {

            boardService.updateReport_count(user_number, music_title);
            Writer out = response.getWriter();
            String message = URLEncoder.encode("신고완료.", "UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            out.write("<script type=\"text/javascript\">alert(decodeURIComponent('" + message + "'.replace(/\\+/g, '%20'))); location.href='/musicDetails/" + music_number + "' </script>");
            out.flush();
            response.flushBuffer();
            out.close();
        } else {

            Boolean ok = adminService.reportMusic(user_number, music_title, music_number);
            boardService.updateReport_count(user_number, music_title);

            if (ok == true) {

                Writer out = response.getWriter();
                String message = URLEncoder.encode("신고완료.", "UTF-8");
                response.setContentType("text/html; charset=UTF-8");
                out.write("<script type=\"text/javascript\">alert(decodeURIComponent('" + message + "'.replace(/\\+/g, '%20'))); location.href='/musicDetails/" + music_number + "' </script>");
                out.flush();
                response.flushBuffer();
                out.close();
            }
        }

        return "redirect:/musicDetails/" + music_number;

    }

    @GetMapping("/deleteMusic/{music_number}/{music_title}/{music_singer}")
    public String deleteMusic(@PathVariable int music_number, @PathVariable String music_title, @PathVariable String music_singer) {

        int user_number = boardService.getUserNumByNickname(music_singer).getUser_number();

        if (musicService.alreadyLike2(music_number) != null) {
            musicService.deleteLike2(music_number);
        }

        if (boardService.selectOk(music_number, music_title) != null) {
//            musicService.deleteLike(music_number, user_number);
            adminService.deleteReportMusic(music_number);
        }
        adminService.deleteMusic(music_number);


        return "redirect:/";
    }


}
