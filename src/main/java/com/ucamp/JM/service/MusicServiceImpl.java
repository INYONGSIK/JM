package com.ucamp.JM.service;

import com.ucamp.JM.dao.MusicDAO;
import com.ucamp.JM.dto.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService{

    private final MusicDAO musicDAO;
    @Override
    public ArrayList<Music> selectMusicAll() {
        System.out.println(musicDAO.selectAllMusic());
        return musicDAO.selectAllMusic();
    }
}
