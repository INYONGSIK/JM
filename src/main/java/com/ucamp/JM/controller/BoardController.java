package com.ucamp.JM.controller;

import com.ucamp.JM.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/boardList")
    public String boardList() {

        return "/board/boardList";
    }

    @GetMapping("/boardWriteForm")
    public String boardWriteForm() {

        return "/board/boardWriteForm";
    }

}
