package com.ucamp.JM.service;

import com.ucamp.JM.dao.AdminDAO;
import com.ucamp.JM.dto.Admin;
import com.ucamp.JM.dto.Report;
import com.ucamp.JM.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDAO adminDAO;

    @Override
    public void deleteUser(int user_number) {
        adminDAO.deleteUser(user_number);
    }

    @Override
    public void deleteReport(int report_ID) {
        adminDAO.deleteReport(report_ID);
    }


    @Override
    public ArrayList<Admin> selectAllUserByReport() {
        return adminDAO.selectAllUserByReport();
    }

    @Override
    public ArrayList<Report> selectAllReport() {
        return adminDAO.selectAllReport();
    }

    @Override
    public ArrayList<User> selectAllUser() {
        return adminDAO.selectAllUser();
    }

    @Override
    public ArrayList<Report> selectReportByType(int report_type) {
        return adminDAO.selectReportByType(report_type);
    }
}
