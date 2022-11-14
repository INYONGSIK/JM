package com.ucamp.JM.service.board;

import com.ucamp.JM.dao.BoardDAO;
import com.ucamp.JM.dto.Board;
import com.ucamp.JM.dto.Comments;
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

    @Override
    public ArrayList<Board> boardSearchList(String boardSearchString) {
        return boardDAO.boardSearchList(boardSearchString);
    }

    @Override
    public void editBoard(int dashboard_No, String dashboard_title, String dashboard_content) {
        boardDAO.editBoard(dashboard_No, dashboard_title, dashboard_title);
    }

    @Override
    public void updateView(int dashboard_No) {
        boardDAO.updateView(dashboard_No);
    }

    @Override
    public void comment(int dashboard_No, String comment, String writer) {
        boardDAO.comment(dashboard_No, comment, writer);
    }

    @Override
    public ArrayList<Comments> CommentSelectAll(int dashboard_No) {
        return boardDAO.CommentSelectAll(dashboard_No);
    }

    @Override
    public void deleteComment(int cno, int dashboard_No) {
        boardDAO.deleteComment(cno, dashboard_No);
    }

    @Override
    public boolean reportComment(int user_number, String contents) {
        boardDAO.reportComment(user_number, contents);
        return true;
    }

    @Override
    public User getUserNumByNickname(String user_nickname) {
        return boardDAO.getUserNumByNickname(user_nickname);
    }

    @Override
    public void updateReport_count(int user_number, String contents) {
        boardDAO.updateReport_count(user_number, contents);
    }
}
