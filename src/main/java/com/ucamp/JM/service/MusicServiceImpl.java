
package com.ucamp.JM.service;

import com.ucamp.JM.dao.MusicDAO;
import com.ucamp.JM.dto.Music;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicServiceImpl implements MusicService {

    private final MusicDAO musicDAO;


    @Override
    public ArrayList<Music> selectAllMusic() {
        return musicDAO.selectAllMusic();
    }

    @Override
    public void insertTodayMusic(Music music) {
        musicDAO.insertTodayMusic(music);
    }

}
