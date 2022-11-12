package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Board;
import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface BoardDAO {

    ArrayList<Board> boardSelectAll();

    void insertBoardWrite(Board board);

    User getUserNicknameByEmail(String user_email);

    Board readboard(int dashboard_No);

    void deletedashboard(int dashboard_No);

    ArrayList<Board> boardSearchList(String boardSearchString);

    void editBoard(@Param("dashboard_No") int dashboard_No, @Param("dashboard_title") String dashboard_title, @Param("dashboard_content") String dashboard_content);

    void updateView(int dashboard_No);

}
