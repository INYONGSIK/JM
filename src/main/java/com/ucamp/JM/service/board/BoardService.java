package com.ucamp.JM.service.board;

import com.ucamp.JM.dto.Board;
import com.ucamp.JM.dto.Comments;
import com.ucamp.JM.dto.Report;
import com.ucamp.JM.dto.User;

import java.util.ArrayList;

public interface BoardService {

    // 다인: 게시글 전체 목록 불러오기
    ArrayList<Board> boardSelectAll();

    // 다인: 사용자 이메일로 user_number와 user_nickname 불러오기
    User getUserNicknameByEmail(String user_email);

    // 다인: 게시판 글쓰기
    void insertBoardWrite(Board board);

    // 다인: 게시글 상세보기
    Board readboard(int dashboard_No);

    // 다인: 게시글 삭제하기
    void deletedashboard(int dashboard_No);

    // 다인: 게시글 검색(제목 사용자 내용으로 모두 검색 가능)
    ArrayList<Board> boardSearchList(String boardSearchString);

    // 다인: 게시글 수정하기
    void editBoard(int dashboard_No, String dashboard_title, String dashboard_content);

    // 다인: 게시글 조회수 올리기
    void updateView(int dashboard_No);

    // 다인: 게시글에 댓글 달기
    void comment(int dashboard_No, String comment, String writer);

    // 다인: 게시글에 달린 댓글 전체 불러오기
    ArrayList<Comments> CommentSelectAll(int dashboard_No);

    // 다인: 댓글 삭제하기(본인이 작성한 댓글만 삭제 가능)
    void deleteComment(int cno, int dashboard_No);

    // 다인: 댓글 신고하기
    boolean reportComment(int user_number, String contents);

    // 다인: 유저 닉네임으로 유저넘버 가져오기
    User getUserNumByNickname(String user_nickname);

    // 다인: 신고횟수 올리기
    void updateReport_count(int user_number, String contents);

    // 다인: 이미 신고되어있는지 댓글인지 확인하기
    Report selectOk(int user_number, String contents);

    // 다인: 게시글 10개 보여주기
    ArrayList<Board> boardSelect10();
}
