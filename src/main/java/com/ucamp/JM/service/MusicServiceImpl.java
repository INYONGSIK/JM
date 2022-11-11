package com.ucamp.JM.service;

import com.ucamp.JM.dao.MusicDAO;
import com.ucamp.JM.dto.AccumulMusic;
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

<<<<<<< HEAD
    //세영: 추후 사용 예정 (화면에 보여줄 list)
=======

>>>>>>> f2426c85a1eb56296f654f2cca2f46ce5e97fcfd
    @Override
    public ArrayList<Music> selectAllMusic() {
        return musicDAO.selectAllMusic();
    }


    @Override
    public void insertTodayMusic(Music music) {
        musicDAO.insertTodayMusic(music);
    }

    @Override
    public void insertMusic(Music music) {
        musicDAO.insertMusic(music);
    }

    @Override
    public void insertAccumulMusic(Music music) {
        musicDAO.insertAccumulMusic(music);
    }


    @Override
    public void updateAccumulMusic(AccumulMusic music) {
        musicDAO.updateAccumulMusic(music);
    }

    @Override
    public void updateAccumulMusicLikeToZero() {
        musicDAO.updateAccumulMusicLikeToZero();
    }

    @Override
    public void insertMonthMusic(AccumulMusic music) {
        music.setAccumul_music_like(music.getMusic_like() + music.getCurrent_music_like());
        musicDAO.insertMonthMusic(music);
    }

}
