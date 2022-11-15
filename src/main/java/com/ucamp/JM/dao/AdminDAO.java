package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Admin;
import com.ucamp.JM.dto.Report;
import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface AdminDAO {

    @Delete("delete from jmuser where user_number=#{user_number}")
    void deleteUser(int user_number);

    ArrayList<Admin> selectAllUserByReport();

    @Select("select * from jmuser order by type")
    ArrayList<User> selectAllUser();

    @Select("select * from report")
    ArrayList<Report> selectAllReport();

    //조치 완료된 신고 내역 삭제
    @Delete("delete from report where report_ID=#{report_ID}")
    void deleteReport(int report_ID);


    ArrayList<Report> selectReportByType(int report_type);


}
