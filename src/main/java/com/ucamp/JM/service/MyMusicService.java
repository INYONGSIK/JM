package com.ucamp.JM.service;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.MyMusic;

import java.util.ArrayList;

public interface MyMusicService {
    ArrayList<MyMusic> myMusicList(MyMusic paramDto);

    void insertMyMusic(Music music);

    void delMyMusic(int MyMusic_number);

    ArrayList<Music> getMusicByMusicSinger(String music_singer);

    int maxMusicNumber();
}
