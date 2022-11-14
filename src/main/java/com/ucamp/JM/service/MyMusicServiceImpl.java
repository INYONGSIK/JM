package com.ucamp.JM.service;

import com.ucamp.JM.dao.MyMusicDAO;
import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.MyMusic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyMusicServiceImpl implements MyMusicService {
    @Autowired
    private MyMusicDAO myMusicDAO;


    @Override
    public ArrayList<MyMusic> myMusicList(MyMusic paramDto) {
        return myMusicDAO.myMusicList(paramDto);
    }

    @Override
    public void insertMyMusic(Music music) {
        myMusicDAO.insertMyMusic(music);
    }

    @Override
    public void delMyMusic(int MyMusic_number) {
        myMusicDAO.delMyMusic(MyMusic_number);
    }

    @Override
    public ArrayList<Music> getMusicByMusicSinger(String music_singer) {
        return myMusicDAO.getMusicByMusicSinger(music_singer);
    }
}
