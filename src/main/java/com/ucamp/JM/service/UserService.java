package com.ucamp.JM.service;

import com.ucamp.JM.dto.User;

public interface UserService {

    User register(User user) throws Exception;

    boolean login(String user_email, String user_password) throws Exception;
}
