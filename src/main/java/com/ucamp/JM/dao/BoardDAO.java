package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Board;
import com.ucamp.JM.dto.Comments;
import com.ucamp.JM.dto.Report;
import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface BoardDAO {

    // 다인: 게시글 전체 목록 불러오기
    ArrayList<Board> boardSelectAll();

    // 다인: 게시판 글쓰기
    void insertBoardWrite(Board board);

    // 다인: 사용자 이메일로 user_number와 user_nickname 불러오기
    User getUserNicknameByEmail(String user_email);

    // 다인: 게시글 상세보기
    Board readboard(int dashboard_No);

    // 다인: 게시글 삭제하기
    void deletedashboard(int dashboard_No);

    // 다인: 게시글 검색(제목 사용자 내용으로 모두 검색 가능)
    ArrayList<Board> boardSearchList(String boardSearchString);

    // 다인: 게시글 수정하기
    void editBoard(@Param("dashboard_No") int dashboard_No, @Param("dashboard_title") String dashboard_title, @Param("dashboard_content") String dashboard_content);

    // 다인: 게시글 조회수 올리기
    void updateView(int dashboard_No);

    // 다인: 게시글에 댓글 달기
    void comment(@Param("dashboard_No") int dashboard_No, @Param("comment") String comment, @Param("writer") String writer);

    // 다인: 게시글에 달린 댓글 전체 불러오기
    ArrayList<Comments> CommentSelectAll(int dashboard_No);

    // 다인: 댓글 삭제하기(본인이 작성한 댓글만 삭제 가능)
    void deleteComment(@Param("cno") int cno, @Param("dashboard_No") int dashboard_No);

    // 다인: 댓글 신고하기
    Boolean reportComment(@Param("user_number") int user_number, @Param("contents") String contents, @Param("dashboard_No") int dashboard_No);

    // 다인: 유저 닉네임으로 유저넘버 가져오기
    User getUserNumByNickname(String user_nickname);

    // 다인: 신고횟수 올리기
    void updateReport_count(@Param("user_number") int user_number, @Param("contents") String contents);

    // 다인: 게시글 10개 보여주기
    ArrayList<Board> boardSelect10();

    // 다인: 이미 신고되어있는지 댓글인지 확인하기
    Report selectOk(@Param("user_number") int user_number, @Param("contents") String contents);

    void deleteReportComment(@Param("dashboard_No") int dashboard_No, @Param("contents") String contents);

}
