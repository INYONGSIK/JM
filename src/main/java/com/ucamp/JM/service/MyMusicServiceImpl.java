package com.ucamp.JM.service;

import com.ucamp.JM.dao.MyMusicDAO;
import com.ucamp.JM.dto.MyMusic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyMusicServiceImpl implements MyMusicService {
    @Autowired
    private MyMusicDAO myMusicDAO;


    @Override
    public ArrayList<MyMusic> selectMyMusic() {
        return myMusicDAO.selectMyMusic();
    }

    @Override
    public void insert(MyMusic myMusic) {
        myMusicDAO.insert(myMusic);
    }

    @Override
    public void delete(int MyMusic_number) {
        myMusicDAO.delete(MyMusic_number);
    }
}
