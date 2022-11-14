package com.ucamp.JM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Report {


    private int user_number;
    private int report_ID;
    //report_type 1이면 댓글, 2면 음원  --이현호
    private int report_type;
    //스키마에 sysdate써서 현재 시각으로 자동 데이터값 들어감  --이현호
    private String report_date;
    private int report_count;
    private String contents;
    private int dashboard_No;
}
