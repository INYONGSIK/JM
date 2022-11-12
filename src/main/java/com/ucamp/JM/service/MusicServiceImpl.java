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

    @Override
    public ArrayList<Music> selectAllMusic() {
        return musicDAO.selectAllMusic();
    }

    @Override
    public ArrayList<Music> selectKeyword(String keyword) {
        return musicDAO.selectKeyword(keyword);
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
