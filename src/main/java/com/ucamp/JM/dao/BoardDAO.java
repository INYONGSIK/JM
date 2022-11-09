package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Board;
import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BoardDAO {

    ArrayList<Board> boardSelectAll();

    void insertBoardWrite(Board board);

    User getUserNicknameByEmail(String user_email);


}
