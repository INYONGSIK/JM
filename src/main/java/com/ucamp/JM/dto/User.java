package com.ucamp.JM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int user_number;
    private String user_email;
    private String user_nickname;
    private String user_password;
    private String user_name;
    private String user_birthday;
    private String user_phone_number;
    private String user_genre;
    private String user_image;
    private String user_like;
    private String type;
}

