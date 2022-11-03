package com.ucamp.JM.service;

import com.ucamp.JM.dao.UserDAO;
import com.ucamp.JM.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public ArrayList<User> selectUser() {
        return userDAO.selectAll();
    }


}
