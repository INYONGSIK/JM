package com.ucamp.JM.dao;

import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    // 용식: 이메일로 유저 정보 가져오기
    User getUserInfoByEmail(String user_email);

    // 용식: 회원가입 유저 정보 insert
    void insertUser(User user) throws Exception;

    // 용식 : 회원탈퇴
    void deleteUser(String user_email, String user_password) throws Exception;

    // 용식 :비밀번호 변경
    void updatePassword(String user_email, String user_password) throws Exception;


}
