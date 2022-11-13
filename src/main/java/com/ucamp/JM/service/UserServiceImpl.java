package com.ucamp.JM.service;

import com.ucamp.JM.dao.UserDAO;
import com.ucamp.JM.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final JavaMailSender javaMailSender;

    @Override
    public User queryUser(String user_email) throws Exception {
        return userDAO.getUserInfoByEmail(user_email);
    }

    @Override
    public User register(User user) throws Exception {
        User userEmail = queryUser(user.getUser_email());
        if (userEmail != null) {
            throw new Exception("이미 있는 이메일 입니다.");
        }
        userDAO.insertUser(user);
        return user;
    }

    @Override
    public boolean login(String user_email, String user_password) throws Exception {
        try {
            User user = queryUser(user_email);
            if (user == null) {
                return false;
            }
            if (user.getUser_password().equals(user_password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new Exception("로그인 오류");
        }
    }

    @Override
    public boolean emailOverLapCheck(String user_email) throws Exception {
        User user = queryUser(user_email);
        // db에 유저가 없으면 false
        if (user == null) return false;
        // 유저가 있으면 중복된 이메일임
        return true;
    }

    @Override
    public boolean nicknameOverLapCheck(String user_nickname) throws Exception {
        User user = userDAO.getUserInfoByNickname(user_nickname);
        if (user == null) return false;
        return true;
    }

    @Override
    public void deleteUser(String user_email, String user_password) throws Exception {
        userDAO.deleteUser(user_email, user_password);
    }

    @Override
    public void modifyPassword(String user_email, String user_password) throws Exception {
        userDAO.updatePassword(user_email, user_password);
    }

    // 이메일 인증 보내기
    // 인증번호 return
    @Override
    public String sendMailForFindPw(String user_email) throws Exception {
        Random random = new Random();
        int checkNum = random.nextInt(999999);
        String setFrom = "dlsdydtlr@gmail.com";
        String toMail = user_email;
        String title = ("[JM]인증이메일 입니다.");
        String content = "<h1>인증번호는" + checkNum + "입니다.</h1>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom(setFrom);
        helper.setTo(toMail);
        helper.setSubject(title);
        helper.setText(content, true);
        javaMailSender.send(message);

        String num = Integer.toString(checkNum);
        return num;
    }

    // 아이디 찾을 때 이름과 휴대폰번호를 이용해 이메일이 있는지 확인 : boolean
    @Override
    public boolean findId(String user_name, String user_phone_number) throws Exception {
        User user = userDAO.getEmailByNameAndPhone(user_name, user_phone_number);
        if (user == null) return false;
        return true;
    }

    // 아이디 찾을 때 이름과 휴대폰번호를 이용해 이메일 return
    @Override
    public User findId2(String user_name, String user_phone_number) throws Exception {
        return userDAO.getEmailByNameAndPhone(user_name, user_phone_number);
    }

    @Override
    public void modifyUserInfo(User user) throws Exception {
        userDAO.updateUser(user);
    }
}
