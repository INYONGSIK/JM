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
}
