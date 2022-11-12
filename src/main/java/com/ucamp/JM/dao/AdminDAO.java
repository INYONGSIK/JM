package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Admin;
import com.ucamp.JM.dto.Report;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface AdminDAO {

    @Delete("delete from jmuser where user_number=#{user_number}")
    void deleteUser(int user_number);

    ArrayList<Admin> selectAllUser();

    @Select("select * from report")
    ArrayList<Report> selectAllReport();


    ArrayList<Report> selectReportByType(int report_type);


}
