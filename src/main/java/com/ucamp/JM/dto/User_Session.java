package com.ucamp.JM.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@ToString
public class User_Session {
    private int user_number;
    private String user_email;
    private String user_session_id;
}

