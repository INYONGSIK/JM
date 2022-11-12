package com.ucamp.JM.service;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.dto.Music;

import java.util.ArrayList;


public interface MusicService {

    //현호 : 검색 키워드 가져와서 비슷한 검색 결과 추출
    ArrayList<Music> selectKeyword(String keyword);

    //세영: music 테이블에서 가져온 값을 today_music 테이블에 넣음
    void updateTodayMusic(Music music);

    void insertMusic(Music music);

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

