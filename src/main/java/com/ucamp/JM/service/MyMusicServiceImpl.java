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
    public ArrayList<MyMusic> myUploadMusicList(MyMusic paramDto) {
        return myMusicDAO.myUploadMusicList(paramDto);
    }

    @Override
    public ArrayList<MyMusic> myLikeMusicList(MyMusic paramDto) {
        return myMusicDAO.myLikeMusicList(paramDto);
    }

    @Override
    public void insertMyMusic(Music music) {
        myMusicDAO.insertMyMusic(music);
    }


    @Override
    public void delMyMusic(int music_number) {
        System.out.println("impl:" + music_number);
        myMusicDAO.delMyMusic(music_number);
    }

    @Override
    public ArrayList<Music> getMusicByMusicSinger(String music_singer) {
        return myMusicDAO.getMusicByMusicSinger(music_singer);
    }

    //@Override
    //public int maxMusicNumber() {
    //    return myMusicDAO.maxMusicNumber();
    //}


}
