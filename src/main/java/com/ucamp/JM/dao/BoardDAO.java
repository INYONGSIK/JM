package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BoardDAO {

    ArrayList<Board> boardSelectAll();

    void boardWriteInput(Board board);

}
