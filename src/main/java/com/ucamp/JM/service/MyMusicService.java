package com.ucamp.JM.service;

import com.ucamp.JM.dto.MyMusic;

import java.util.ArrayList;

public interface MyMusicService {
    ArrayList<MyMusic> myMusicList(MyMusic paramDto);

    void insertMyMusic(MyMusic myMusic);

    void delete(int MyMusic_number);
}
