package com.ucamp.JM.service.board;

import com.ucamp.JM.dto.Board;
import com.ucamp.JM.dto.User;

import java.util.ArrayList;

public interface BoardService {
    ArrayList<Board> boardSelectAll();

    User getUserNicknameByEmail(String user_email);

    void insertBoardWrite(Board board);
}
