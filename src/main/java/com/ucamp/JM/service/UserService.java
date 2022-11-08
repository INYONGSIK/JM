package com.ucamp.JM.service;

import com.ucamp.JM.dto.User;

public interface UserService {
    // 용식 : 유저이메일로 유저정보 가져오기
    User queryUser(String user_email) throws Exception;

    // 용식 : 회원가입
    User register(User user) throws Exception;

    // 용식 : 로그인
    boolean login(String user_email, String user_password) throws Exception;

    // 용식 : 이메일 중복 체크
    boolean emailOverLapCheck(String user_email) throws Exception;

    void deleteUser(String user_email, String user_password) throws Exception;


}
