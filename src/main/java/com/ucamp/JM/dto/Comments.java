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
public class Comments {
    private int cno;
    private int dashboard_no;
    private String content;
    private Date createat;
    private String writer;


}
