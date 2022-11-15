package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.MyMusic;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MyMusicDAO {

    ArrayList<Music> getMusicByMusicSinger(String music_singer);

    ArrayList<MyMusic> myUploadMusicList(MyMusic paramDto);

    ArrayList<MyMusic> myLikeMusicList(MyMusic paramDto);

    void insertMyMusic(Music music);

    void delMyMusic(int music_singer);

    //int maxMusicNumber();
}

