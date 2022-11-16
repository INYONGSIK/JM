package com.ucamp.JM.service;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.MyMusic;

import java.util.ArrayList;

public interface MyMusicService {
    ArrayList<MyMusic> myUploadMusicList(MyMusic paramDto);

    Music myLikeMusicList(int music_number);

    void insertMyMusic(Music music);

    void insertMyMusicToday(Music music);

    void insertMyMusicAccumul(Music music);

    void delMyMusic(int music_number);

    ArrayList<Music> getMusicByMusicSinger(String music_singer);

    //int maxMusicNumber();
}

