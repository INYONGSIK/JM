package com.ucamp.JM.service;

import com.ucamp.JM.dto.Music;

import java.util.ArrayList;


public interface MusicService {
    //세영: 추후 사용 예정 (화면에 보여줄 list)
    ArrayList<Music> selectAllMusic();

    //세영: music 테이블에서 가져온 값을 today_music 테이블에 넣음
    void insertTodayMusic(Music music);
}

