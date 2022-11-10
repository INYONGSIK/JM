package com.ucamp.JM.service.board;

import com.ucamp.JM.dao.BoardDAO;
import com.ucamp.JM.dto.Board;
import com.ucamp.JM.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardDAO boardDAO;

    @Override
    public ArrayList<Board> boardSelectAll() {
        return boardDAO.boardSelectAll();
    }

    @Override
    public User getUserNicknameByEmail(String user_email) {
        return boardDAO.getUserNicknameByEmail(user_email);
    }

    @Override
    public void insertBoardWrite(Board board) {
        boardDAO.insertBoardWrite(board);
    }

    @Override
    public Board readboard(int dashboard_No) {
        return boardDAO.readboard(dashboard_No);
    }

    @Override
    public void deletedashboard(int dashboard_No) {
        boardDAO.deletedashboard(dashboard_No);
    }
}
