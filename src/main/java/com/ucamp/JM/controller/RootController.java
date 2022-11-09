package com.ucamp.JM.controller;

import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.SendAuthNumberPhone;
import com.ucamp.JM.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(RootController.class);

    @GetMapping("/")
    public String main() {
        return "main";
    }

    // 회원가입 폼
    @GetMapping("/registerform")
    public String registerform() {
        return "registerform";
    }

    // 용식 :회원가입
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        try {
            // 회원가입시 유저타입(admin or user) user 로 설정
            user.setType("user");
            userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    // 용식 : 회원가입 문자정송
    @ResponseBody
    @GetMapping("/phoneCheck")
    public String sendSMS(@RequestParam("user_phone_number") String userPhoneNumber) { // 휴대폰 문자보내기
        int randomNumber = (int) ((Math.random() * (9999 - 1000 + 1)) + 1000);//난수 생성
        SendAuthNumberPhone phone = new SendAuthNumberPhone();
        phone.certifiedPhoneNumber(userPhoneNumber, randomNumber);
        System.out.println(randomNumber);
        return Integer.toString(randomNumber);
    }

    // 로그인 폼
    @GetMapping("/loginform")
    public String loginform() {
        return "loginform";
    }

    // 용식 : 로그인
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

    // 용식 : 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    // 용식 : 이메일 중복확인
    @ResponseBody
    @PostMapping("/emailoverlap")
    public boolean emailoverlap(@RequestParam String user_email) {
        boolean result = false;
        try {
            // 중복된 이메일이 있으면 result = true
            result = userService.emailOverLapCheck(user_email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 용식 : 닉네임 중복확인
    @ResponseBody
    @PostMapping("/nicknameoverlap")
    public boolean nicknameoverlap(@RequestParam String user_nickname) {
        boolean result = false;
        try {
            result = userService.nicknameOverLapCheck(user_nickname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 용식 : 회원탈퇴
    @ResponseBody
    @PostMapping("/deleteuser")
    public String deleteuser(@RequestParam String user_email, @RequestParam String user_password) {
        try {
            User user = userService.queryUser(user_email);
            if (user == null) {
                return "시용자없음";
            }
            if (!(user.getUser_password().equals(user_password))) {
                // 비밀번호 틀리면 삭제실패
                return "삭제실패";
            } else {
                userService.deleteUser(user_email, user_password);
                return "삭제성공";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "에러";
        }
    }

    // 용식 : 비밀번호찾기 눌렀을 떄 나오는 폼
    @GetMapping("/findpwform")
    public String findpwform() {
        return "findpwform";
    }

    // 용식 : 이메일 보내기
    @ResponseBody
    @GetMapping("/sendMail")
    public String sendMail(@RequestParam String user_email) {
        String result = "";
        try {
            User user = userService.queryUser(user_email);
            if (user.getUser_email() != null) {
                result = userService.sendMailForFindPw(user_email);
            } else {
                result = "유저없음";
            }

        } catch (Exception e) {
            result = "에러";
            e.printStackTrace();
        }
        return result;
    }

    // 용식 : 비밀번호 찾기에서 이메일인증시 비밀번호 바꾸는 폼
    @GetMapping("/modifypasswordform")
    public String modifypasswordform(@RequestParam(required = false) String user_email, Model model) {
        model.addAttribute("user_email", user_email);
        return "modifypasswordform";
    }

    // 용식 : 비밀번호 변경
    @GetMapping("/modifyPassword")
    public String modifyPassword(@RequestParam String user_email, @RequestParam String user_password, Model model) {
        try {
            logger.info("user_email =>" + user_email);
            logger.info("user_password =>" + user_password);
            userService.modifyPassword(user_email, user_password);
            System.out.println("성공");
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/findidform")
    public String findidform() {
        return "findidform";
    }
}





