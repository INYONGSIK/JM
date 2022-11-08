package com.ucamp.JM.service.board;

import com.ucamp.JM.dao.BoardDAO;
import com.ucamp.JM.dto.Board;
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
}
