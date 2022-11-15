package com.ucamp.JM.service;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.dto.Music;

import java.util.ArrayList;


public interface MusicService {

    //현호 : 검색 키워드 가져와서 비슷한 검색 결과 추출

    ArrayList<Music> SearchByKeyword(String keyword, String genre);

    //현호 : 음악 상세페이지 검색
    Music showMusicDetails(int music_number);

    //현호 : 좋아요 누를 시 뮤직테이블 like 1증가
    void likeIncrement(int music_number);


    //세영: music 테이블에서 가져온 값을 today_music 테이블에 넣음
    void updateTodayMusic(Music music);


    void insertAccumulMusic(Music music);

    void updateAccumulMusic(AccumulMusic music);

    void updateAccumulMusicLikeToZero();

    void insertWeekMusic(Music music);

    void insertMonthMusic(AccumulMusic music);

    void updateWeekMusicLikeToZero();

    ArrayList<Music> selectTopMusic();

    ArrayList<Music> selectTopMusicByGenre(String genre);

    ArrayList<Music> selectTopMusicByDate(String dateValue);

    ArrayList<Music> selectTopMusicByDateAndGenre(String date, String genre);


}

