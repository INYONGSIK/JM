package com.ucamp.JM.dao;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.dto.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface MusicDAO {

    void insertAccumulMusic(Music music);

    ArrayList<Music> selectMusic();

    //현호 = 검색하는 서비스 매퍼
    ArrayList<Music> SearchByKeyword(@Param("keyword") String keyword, @Param("genre") String genre);


    ArrayList<Music> selectAllMusic();

    void insertTodayMusic(Music music);

    void insertMusic(Music music);

    void updateAccumulMusic(AccumulMusic music);

    void updateAccumulMusicLikeToZero();

    void insertMonthMusic(AccumulMusic music);
}
