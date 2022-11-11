package com.ucamp.JM.service;

import com.ucamp.JM.dto.MyMusic;

import java.util.ArrayList;

public interface MyMusicService {
    ArrayList<MyMusic> selectMyMusic();

    void insert(MyMusic myMusic);

    void delete(int MyMusic_number);
}
