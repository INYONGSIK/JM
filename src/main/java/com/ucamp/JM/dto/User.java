package com.ucamp.JM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int user_number;
    private String user_email;
    private String user_nickname;
    private String user_password;
    private String user_name;


}
