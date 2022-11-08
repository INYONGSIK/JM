package com.ucamp.JM.service;

import com.ucamp.JM.dao.UserDAO;
import com.ucamp.JM.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public User register(User user) throws Exception {
        User userEmail = userDAO.getUserInfoByEmail(user.getUser_email());
        if (userEmail != null) {
            throw new Exception("이미 있는 이메일 입니다.");
        }
        userDAO.insertUser(user);
        return user;
    }

    @Override
    public boolean login(String user_email, String user_password) throws Exception {
        try {
            User user = userDAO.getUserInfoByEmail(user_email);
            if (user == null) throw new Exception("없는 유저");
            if (user.getUser_password().equals(user_password)) {
                return true;
            } else {
                throw new Exception("비밀번호가 틀림");
            }
        } catch (Exception e) {
            throw new Exception("로그인 오류");
        }
    }
}
