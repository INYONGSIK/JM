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

    // 다인 : 게시판 글 목록 보여주기
    @GetMapping("/boardList")
    public String boardList(Model model) {
        model.addAttribute("dashboards", boardService.boardSelectAll());


        return "/board/boardList";
    }

    // 다인 : 게시판 글 쓰기 폼으로 이동
    @GetMapping("/boardWriteForm")
    public String boardWriteForm(HttpSession session, Model model) {
        String user_email = (String) session.getAttribute("user_email");

        model.addAttribute("dashboard_user", boardService.getUserNicknameByEmail(user_email).getUser_nickname());
        model.addAttribute("user_number", boardService.getUserNicknameByEmail(user_email).getUser_number());


        return "/board/boardWriteForm";
    }

    // 다인 : 게시판 글 쓰기 기능
    @PostMapping("/boardWriteInput")
    public String insertBoardWrite(Model model, Board board) {

        boardService.insertBoardWrite(board);
        return "redirect:/boardList";
    }


    // 다인 : 게시판 글 상세보기, 댓글 보기
    @RequestMapping("/readboard/{dashboard_No}")
    public String readboard(HttpServletRequest request, Model model, @PathVariable int dashboard_No) {
        boardService.updateView(dashboard_No);

        Board board = boardService.readboard(dashboard_No);

        String user_email = (String) request.getSession().getAttribute("user_email");

        if (user_email != null) {
            if (user_email.equals("admin@aaa.com")) {
                model.addAttribute("admin", "admin@aaa.com");
            } else {
                String sessionName = boardService.getUserNicknameByEmail(user_email).getUser_nickname();
                model.addAttribute("sessionName", sessionName);
            }
        }
        model.addAttribute("comments", boardService.CommentSelectAll(dashboard_No));


        model.addAttribute("dashboard_No", dashboard_No);
        model.addAttribute("user_number", board.getUser_number());
        model.addAttribute("dashboard_user", board.getDashboard_user());
        model.addAttribute("dashboard_title", board.getDashboard_title());
        model.addAttribute("dashboard_content", board.getDashboard_content());


        return "/board/readboard";

    }

    // 다인 : 게시판 글 삭제
    @GetMapping("/deletedashboard/{dashboard_No}/{dashboard_user}")
    public String deletedashboard(HttpSession session, HttpServletResponse response, @PathVariable int dashboard_No, @PathVariable String dashboard_user) throws IOException {

        String user_email = (String) session.getAttribute("user_email");
        //현재 세션에 들어와있는(로그인 되어있는 사용자의 이름)
        String login_user_nickname = boardService.getUserNicknameByEmail(user_email).getUser_nickname();

        System.out.println("dashboard_user" + dashboard_user);
        System.out.println("login_user" + login_user_nickname);

        if (dashboard_user.equals(login_user_nickname)) {
            if (boardService.CommentSelectAll(dashboard_No) != null) {
                boardService.deleteCommentAll2(dashboard_No);
            }
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
//        return "board/boardList";
    }

    // 다인 : 게시판 글 검색
    @GetMapping("/boardSearchList")
    public String boardSearchList(@RequestParam String boardSearchString, Model model) {

        ArrayList<Board> boardsearchList = boardService.boardSearchList(boardSearchString);

        model.addAttribute("dashboards", boardsearchList);

        return "/board/boardList";
    }


    // 다인 : 게시판 글 수정하는 폼으로 이동
    @GetMapping("/editBoardForm/{dashboard_No}")
    public String editBoardForm(HttpServletResponse response, HttpServletRequest request, @PathVariable int dashboard_No, Model model) throws IOException {


        Board board1 = boardService.readboard(dashboard_No);

        String user_email = (String) request.getSession().getAttribute("user_email");
        String login_user_nickname = boardService.getUserNicknameByEmail(user_email).getUser_nickname();
        String dashboard_user = boardService.readboard(dashboard_No).getDashboard_user();

        if (login_user_nickname.equals(dashboard_user)) {
            model.addAttribute("dashboard_user", board1.getDashboard_user());
            model.addAttribute("dashboard_title", board1.getDashboard_title());
            model.addAttribute("dashboard_content", board1.getDashboard_content());
            return "/board/editBoardForm";

        } else {
            Writer out = response.getWriter();
            String message = URLEncoder.encode("작성자만 수정 가능합니다. 게시판 목록으로 이동합니다.", "UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            out.write("<script type=\"text/javascript\">alert(decodeURIComponent('" + message + "'.replace(/\\+/g, '%20'))); location.href='/boardList' </script>");
            out.flush();
            response.flushBuffer();
            out.close();
        }

        return "/board/editBoardForm";
    }

    // 다인 : 게시판 글 수정 기능
    @GetMapping("/editBoard")
    public String editBoard(HttpServletResponse response, HttpServletRequest request, @RequestParam int dashboard_No, @RequestParam String dashboard_title, @RequestParam String dashboard_content) throws IOException {
        Board board1 = boardService.readboard(dashboard_No);

        String user_email = (String) request.getSession().getAttribute("user_email");
        String login_user_nickname = boardService.getUserNicknameByEmail(user_email).getUser_nickname();
        String dashboard_user = boardService.readboard(dashboard_No).getDashboard_user();

        if (login_user_nickname.equals(dashboard_user)) {
            board1.setDashboard_title(request.getParameter("dashboard_title"));
            board1.setDashboard_content(request.getParameter("dashboard_content"));

            boardService.editBoard(dashboard_No, dashboard_title, dashboard_content);
        } else {
            Writer out = response.getWriter();
            String message = URLEncoder.encode("작성자만 수정 가능합니다. 게시판 목록으로 이동합니다.", "UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            out.write("<script type=\"text/javascript\">alert(decodeURIComponent('" + message + "'.replace(/\\+/g, '%20'))); location.href='/boardList' </script>");
            out.flush();
            response.flushBuffer();
            out.close();
        }


        return "redirect:/boardList";
    }

    // 다인 : 댓글달기
    @GetMapping("/comment")
    public String comment(@RequestParam String comment, @RequestParam String writer, @RequestParam int dashboard_No) {

        boardService.comment(dashboard_No, comment, writer);

        return "redirect:/readboard/" + dashboard_No;
    }


    // 다인 : 댓글 삭제
    @GetMapping("/deleteComment/{dashboard_No}/{cno}/{contents}/{writer}")
    public String deleteComment(@PathVariable int dashboard_No, @PathVariable int cno, @PathVariable String contents, @PathVariable String writer) {
        System.out.println(writer);

        int user_number = boardService.getUserNumByNickname(writer).getUser_number();

        if (boardService.selectOk(user_number, contents) != null) {


            boardService.deleteReportComment(dashboard_No, contents);

            ;
        }
        boardService.deleteComment(cno, dashboard_No);

        return "redirect:/readboard/" + dashboard_No;
    }


    // 다인 : 댓글 신고
    @GetMapping("/reportComment/{writer}/{contents}/{dashboard_No}")
    public String reportComment(HttpServletResponse response, @PathVariable String writer, @PathVariable String contents, @PathVariable int dashboard_No) throws IOException {

        int user_number = boardService.getUserNumByNickname(writer).getUser_number();

        if (boardService.selectOk(user_number, contents) != null) {

            boardService.updateReport_count(user_number, contents);
            Writer out = response.getWriter();
            String message = URLEncoder.encode("신고완료.", "UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            out.write("<script type=\"text/javascript\">alert(decodeURIComponent('" + message + "'.replace(/\\+/g, '%20'))); location.href='/boardList' </script>");
            out.flush();
            response.flushBuffer();
            out.close();
        } else {

            Boolean ok = boardService.reportComment(user_number, contents, dashboard_No);
            boardService.updateReport_count(user_number, contents);

            if (ok == true) {

                Writer out = response.getWriter();
                String message = URLEncoder.encode("신고완료.", "UTF-8");
                response.setContentType("text/html; charset=UTF-8");
                out.write("<script type=\"text/javascript\">alert(decodeURIComponent('" + message + "'.replace(/\\+/g, '%20'))); location.href='/boardList' </script>");
                out.flush();
                response.flushBuffer();
                out.close();
            }
        }

        return "board/boardList";
//        return "redirect:/readboard/" + dashboard_No;
//        return "";

    }


}
