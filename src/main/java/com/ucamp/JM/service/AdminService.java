package com.ucamp.JM.service;

import com.ucamp.JM.dto.Admin;
import com.ucamp.JM.dto.Report;

import java.util.ArrayList;

public interface AdminService {

    //회원넘버 받아와서 회원 삭제       --이현호
    void deleteUser(int user_number);

    //admin 회원관리 페이지에서 보여지는 회원 조회       --이현호
    ArrayList<Admin> selectAllUser();

    //report 관련 항목 가져오기
    ArrayList<Report> selectAllReport();


    //이현호
    //admin 신고관리 페이지에서 받는 댓글(report_type=1), 음원(report_type=2)관련 정보
    ArrayList<Report> selectReportByType(int report_type);


}
