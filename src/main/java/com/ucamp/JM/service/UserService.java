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

    // 용식 : 닉네임 중복 체크
    boolean nicknameOverLapCheck(String user_nickname) throws Exception;

    // 용식 : 회원탈퇴
    void deleteUser(String user_email, String user_password) throws Exception;

    // 용식 : 비밀번호변경 (비밀번호 찾기후 변경)
    void modifyPassword(String user_email, String user_password) throws Exception;

    // 용식 : 비밀번호 찾기 이메일보내기
    String sendMailForFindPw(String email) throws Exception;

    // 용식 : 이름과 휴대폰번호로 아이디찾기가 있는지 확인
    boolean findId(String user_name, String user_phone_number) throws Exception;

    // 용식 : 이름과 휴대폰번호로 아이디찾기
    User findId2(String user_name, String user_phone_number) throws Exception;
}
