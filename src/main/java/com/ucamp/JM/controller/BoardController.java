package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Board;
import com.ucamp.JM.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BoardController {


    @Autowired
    BoardService boardService;

    @GetMapping("/boardList")
    public String boardList() {
        boardService.boardSelectAll();


        return "/board/boardList";
    }

    @GetMapping("/boardWriteForm")
    public String boardWriteForm(HttpSession session, Model model) {
        String user_email = (String) session.getAttribute("user_email");

        System.out.println(user_email);

//        User nickname = boardService.getUserNicknameByEmail(user_email)
        model.addAttribute("dashboard_user", boardService.getUserNicknameByEmail(user_email).getUser_nickname());
        model.addAttribute("user_number", boardService.getUserNicknameByEmail(user_email).getUser_number());
        System.out.println(boardService.getUserNicknameByEmail(user_email));

        return "/board/boardWriteForm";
    }

    @PostMapping("/boardWriteInput")
    public String insertBoardWrite(Model model, Board board) {
        System.out.println(board);
        boardService.insertBoardWrite(board);
        return "/";
    }

}
