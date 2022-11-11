package com.ucamp.JM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {
    private int user_number;
    private String user_email;
    private String user_name;
    private String user_phone_number;
    private String user_birthday;
    private int report_count;
}
