package com.ucamp.JM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {
    private int dashboard_No;
    private int user_number;
    private String dashboard_title;
    private String dashboard_user;
    private Date dashboard_date;
    private int dashboard_views;
    private String dashboard_comment;
    private String dashboard_content;

}
