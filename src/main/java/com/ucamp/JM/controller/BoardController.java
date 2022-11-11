package com.ucamp.JM.controller;

import com.ucamp.JM.dto.Board;
import com.ucamp.JM.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;

@Controller
public class BoardController {


    @Autowired
    BoardService boardService;

    @GetMapping("/boardList")
    public String boardList(Model model) {
        model.addAttribute("dashboards", boardService.boardSelectAll());


        return "/board/boardList";
    }

    @GetMapping("/boardWriteForm")
    public String boardWriteForm(HttpSession session, Model model) {
        String user_email = (String) session.getAttribute("user_email");

        model.addAttribute("dashboard_user", boardService.getUserNicknameByEmail(user_email).getUser_nickname());
        model.addAttribute("user_number", boardService.getUserNicknameByEmail(user_email).getUser_number());


        return "/board/boardWriteForm";
    }

    @PostMapping("/boardWriteInput")
    public String insertBoardWrite(Model model, Board board) {

        boardService.insertBoardWrite(board);
        return "redirect:/boardList";
    }

    @RequestMapping("/readboard/{dashboard_No}")
    public String readboard(Model model, @PathVariable int dashboard_No) {


        Board board = boardService.readboard(dashboard_No);
        model.addAttribute("dashboard_No", dashboard_No);
        model.addAttribute("dashboard_user", board.getDashboard_user());
        model.addAttribute("dashboard_title", board.getDashboard_title());
        model.addAttribute("dashboard_content", board.getDashboard_content());


        return "/board/readboard";

    }

    @GetMapping("/deletedashboard/{dashboard_No}/{dashboard_user}")
    public String deletedashboard(HttpSession session, HttpServletResponse response, @PathVariable int dashboard_No, @PathVariable String dashboard_user) throws IOException {

        String user_email = (String) session.getAttribute("user_email");
        //현재 세션에 들어와있는(로그인 되어있는 사용자의 이름)
        String login_user_nickname = boardService.getUserNicknameByEmail(user_email).getUser_nickname();

        if (dashboard_user == login_user_nickname) {
            boardService.deletedashboard(dashboard_No);
        } else {
            Writer out = response.getWriter();
            String message = URLEncoder.encode("작성자만 삭제 가능합니다. 게시판 목록으로 이동합니다.", "UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            out.write("<script type=\"text/javascript\">alert(decodeURIComponent('" + message + "'.replace(/\\+/g, '%20'))); location.href='/boardList' </script>");
            out.flush();
            response.flushBuffer();
            out.close();
        }


        return "redirect:/boardList";
    }

    @GetMapping("/boardSearchList")
    public String boardSearchList(@RequestParam String boardSearchString, Model model) {

        ArrayList<Board> boardsearchList = boardService.boardSearchList(boardSearchString);

        model.addAttribute("dashboards", boardsearchList);

        return "/board/boardList";
    }

    @GetMapping("/editBoardForm/{dashboard_No}")
    public String editBoardForm(@PathVariable int dashboard_No, Model model) {

        Board board = boardService.readboard(dashboard_No);
        model.addAttribute("dashboard_user", board.getDashboard_user());
        model.addAttribute("dashboard_title", board.getDashboard_title());
        model.addAttribute("dashboard_content", board.getDashboard_content());

        return "/board/editBoardForm";
    }

    @GetMapping("/editBoard")
    public String editBoard(HttpServletRequest request, @RequestParam int dashboard_No, Board board, Model model) {
        Board board1 = boardService.readboard(dashboard_No);

        Board board2 = new Board();
        board2.setDashboard_title(request.getParameter("dashboard_title"));
        board2.setDashboard_content(request.getParameter("dashboard_content"));

//        model.addAttribute("dashboard_user", board1.getDashboard_user());
//        model.addAttribute("dashboard_title", board1.getDashboard_title());
//        model.addAttribute("dashboard_content", board1.getDashboard_content());

        boardService.editBoard(dashboard_No, board);


        return "redirect:/boardList";
    }

}
