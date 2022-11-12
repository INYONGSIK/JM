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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class RootController {

    private final UserService userService;
    private final HttpSession session;
    private final ServletContext servletContext;
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
    public String register(HttpServletRequest request, MultipartFile user_image) {
        User user = new User();
        user.setUser_email(request.getParameter("user_email"));
        user.setUser_nickname(request.getParameter("user_nickname"));
        user.setUser_password(request.getParameter("user_password"));
        user.setUser_name(request.getParameter("user_name"));
        user.setUser_birthday(request.getParameter("user_birthday"));
        user.setUser_phone_number(request.getParameter("user_phone_number"));
        String[] genres = request.getParameterValues("user_genre");
        List<String> sortGenre = new ArrayList<>();
        if (genres != null) {
            for (String genre : genres) {
                sortGenre.add(genre);
            }
        }
        user.setUser_genre(sortGenre.toString().substring(1, sortGenre.toString().length() - 1).trim());
        user.setUser_image(request.getParameter("user_image"));

        // 회원가입시 유저타입(admin or user) user 로 설정
        user.setType("user");

        try {
            // 원래 파일 이름
            String profile = user_image.getOriginalFilename();
            // 파일이름으로 사용할 uuid 생성
            String uuid = UUID.randomUUID().toString();
            // 확장자 추출 ( ex.png)
            String extension = profile.substring(profile.lastIndexOf(".") + 1);
            // uuid 와 확장자 결합
            String saveProfile = uuid + "." + extension;
            // 저장할 위치
            // servletContext 쓸려면 private final ServletContext servletContext; 해줘여함
            String path = servletContext.getRealPath("/img/profile/");
            File destFile = new File(path + saveProfile);
            user_image.transferTo(destFile);
            user.setUser_image(saveProfile.toString());
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
    public String login(@RequestParam String user_email, @RequestParam String user_password,
                        Model model) throws Exception {
        User user = userService.queryUser(user_email);
        try {
            if (user.getType().equals("user")) {
                userService.login(user_email, user_password);
                session.setAttribute("user_email", user_email);
                //session.setAttribute("userType", type);
                return "redirect:/";
            } else {
                session.setAttribute("user_email", user_email);
                return "redirect:/admin";
            }
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

    // 용식 : 로그인할때 아이디와 비밀번호가 맞는지 확인
    @ResponseBody
    @PostMapping("/valid")
    public String valid(@RequestParam String user_email, @RequestParam String user_password) throws Exception {
        if (userService.login(user_email, user_password)) {
            return "검증완료";
        } else {
            return "검증실패";
        }
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
            if (user != null) {
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

    // 이름과 휴대폰번호가가 일치하고 존재하는지 확인
    @GetMapping("/findidform")
    public String findidform() {
        return "findidform";
    }

    @ResponseBody
    @PostMapping(value = "findid", produces = "application/text; charset=UTF-8")
    public String findid(@RequestParam String user_name, @RequestParam String user_phone_number) {
        String result = "";
        try {
            if (userService.findId(user_name, user_phone_number)) {
                result = "성공";
            } else {
                result = "유저없음";
            }
        } catch (Exception e) {
            result = "에러";
            throw new RuntimeException(e);
        }
        return result;
    }

    // 이름과 휴대폰번호가 일치했을 때 보내줄 사용자 이메일
    @ResponseBody
    @PostMapping("findid2")
    public User findid2(@RequestParam String user_name, @RequestParam String user_phone_number) {
        User user = null;
        try {
            user = userService.findId2(user_name, user_phone_number);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
        return user;
    }

    @GetMapping("/modifyinformationform")
    public String modifyinformation(Model model) {
        String email = (String) session.getAttribute("user_email");
        if (email == null) {
            return "redirect:/";
        }
        try {
            User user = userService.queryUser(email);
            user.setUser_birthday(user.getUser_birthday().substring(0, 10));
            if (user == null) {
                return "/loginform";
            } else {
                model.addAttribute("user", user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "modifyinformationform";
    }

    // 회원정보 수정
    @PostMapping("modifyinformation")
    public String modifyinformation(HttpServletRequest request, MultipartFile user_image) throws Exception {
        String email = (String) session.getAttribute("user_email");
        User info = userService.queryUser(email);

        User user = new User();
        user.setUser_email(request.getParameter("user_email"));
        user.setUser_nickname(request.getParameter("user_nickname"));
        user.setUser_password(request.getParameter("user_password"));
        user.setUser_name(request.getParameter("user_name"));
        user.setUser_birthday(request.getParameter("user_birthday"));
        user.setUser_phone_number(request.getParameter("user_phone_number"));

        String[] genres = request.getParameterValues("user_genre");
        List<String> sortGenre = new ArrayList<>();
        if (genres != null) {
            for (String genre : genres) {
                sortGenre.add(genre);
            }
        }
        user.setUser_genre(sortGenre.toString().substring(1, sortGenre.toString().length() - 1).trim());
        try {
            System.out.println("b: " + user_image.getOriginalFilename());
            // 원래 파일 이름
            if (!user_image.getOriginalFilename().equals("")) {
                String profile = user_image.getOriginalFilename();
                // 파일이름으로 사용할 uuid 생성
                String uuid = UUID.randomUUID().toString();
                // 확장자 추출 ( ex.png)
                String extension = profile.substring(profile.lastIndexOf(".") + 1);
                // uuid 와 확장자 결합
                String saveProfile = uuid + "." + extension;
                // 저장할 위치
                // servletContext 쓸려면 private final ServletContext servletContext; 해줘여함 --내장톰켓써서 패스가 안잡힘
                String path = servletContext.getRealPath("/img/profile/");
                File destFile = new File(path + saveProfile);
                user_image.transferTo(destFile);
                user.setUser_image(saveProfile.toString());
            } else {
                user.setUser_image(info.getUser_image());
            }
            if (user.getUser_password().equals("")) {
                user.setUser_password(info.getUser_password());
            }
            userService.modifyUserInfo(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/modifyinformationform";
    }


}
