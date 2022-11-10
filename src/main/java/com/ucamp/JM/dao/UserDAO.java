package com.ucamp.JM.dao;

import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDAO {

    // 용식: 이메일로 유저 정보 가져오기
    User getUserInfoByEmail(String user_email);

    // 용식 : 닉네임으로 유저 정보 가져오기
    User getUserInfoByNickname(String user_nickname);

    // 용식: 회원가입 유저 정보 insert
    void insertUser(User user) throws Exception;

    // 용식 : 회원탈퇴
    void deleteUser(@Param("user_email") String user_email, @Param("user_password") String user_password) throws Exception;

    // 용식 :비밀번호 변경
    void updatePassword(@Param("user_email") String user_email, @Param("user_password") String user_password) throws Exception;

    // 용식 : 아이디(이메일) 찾기 => 이름과 휴대폰 번호로 이메일 가져오기
    User getEmailByNameAndPhone(@Param("user_name") String user_name, @Param("user_phone_number") String user_phone_number) throws Exception;

}
