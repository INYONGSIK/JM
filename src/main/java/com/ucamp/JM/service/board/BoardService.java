package com.ucamp.JM.service.board;

import com.ucamp.JM.dto.Board;
import com.ucamp.JM.dto.User;

import java.util.ArrayList;

public interface BoardService {
    ArrayList<Board> boardSelectAll();

    User getUserNicknameByEmail(String user_email);

    void insertBoardWrite(Board board);

    Board readboard(int dashboard_No);

    void deletedashboard(int dashboard_No);

    ArrayList<Board> boardSearchList(String boardSearchString);

    void editBoard(int dashboard_No, Board board);

}
