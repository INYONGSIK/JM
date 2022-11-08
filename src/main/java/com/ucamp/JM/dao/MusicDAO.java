package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Music;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MusicDAO {
    ArrayList<Music> selectMusic();

    void insertTodayMusic(Music music);
}
