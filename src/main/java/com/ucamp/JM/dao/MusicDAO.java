package com.ucamp.JM.dao;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.dto.Music;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MusicDAO {
<<<<<<< HEAD
    void insertAccumulMusic(Music music);

    ArrayList<Music> selectAllMusic();

    ArrayList<Music> selectMusic();
=======
    ArrayList<Music> selectAllMusic();
>>>>>>> f2426c85a1eb56296f654f2cca2f46ce5e97fcfd

    void insertTodayMusic(Music music);

    void insertMusic(Music music);

    void updateAccumulMusic(AccumulMusic music);

    void updateAccumulMusicLikeToZero();

    void insertMonthMusic(AccumulMusic music);
}
