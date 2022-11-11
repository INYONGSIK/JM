package com.ucamp.JM.dao;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.dto.Music;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MusicDAO {
    void insertAccumulMusic(Music music);

    ArrayList<Music> selectAllMusic();

    ArrayList<Music> selectMusic();

    void insertTodayMusic(Music music);

    void insertMusic(Music music);

    void updateAccumulMusic(AccumulMusic music);

    void updateAccumulMusicLikeToZero();

    void insertMonthMusic(AccumulMusic music);
}
