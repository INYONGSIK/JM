package com.ucamp.JM.dao;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.dto.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface MusicDAO {

    ArrayList<Music> selectMusic();

    //현호 = 검색하는 서비스 매퍼
    ArrayList<Music> SearchByKeyword(@Param("keyword") String keyword, @Param("genre") String genre);

    void updateTodayMusic(Music music);

    void insertAccumulMusic(Music music);

    void updateAccumulMusic(AccumulMusic music);

    void updateAccumulMusicLikeToZero();

    void insertMonthMusic(AccumulMusic music);

    void insertWeekMusic(Music music);

    void updateWeekMusicLikeToZero();

    //세영 : html에 뿌려주기
    ArrayList<Music> selectTopMusic();

    ArrayList<Music> selectTopMusicByGenre(String genre);

    ArrayList<Music> selectTopMusicByDate(String dateValue);

    ArrayList<Music> selectTopMusicByDateAndGenre(@Param("date") String date, @Param("genre") String genre);

}
