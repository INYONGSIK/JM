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


    //현호 = 검색하는 서비스
    @Override
    public ArrayList<Music> SearchByKeyword(String keyword, String genre) {
        return musicDAO.SearchByKeyword(keyword, genre);

    }

    @Override
    public void updateTodayMusic(Music music) {
        musicDAO.updateTodayMusic(music);
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
        log.info(music.getMusic_title());
        log.info(String.valueOf(music.getAccumul_music_like()));
        musicDAO.insertMonthMusic(music);
    }

    @Override
    public void updateWeekMusicLikeToZero() {
        musicDAO.updateWeekMusicLikeToZero();
    }

    @Override
    public void insertWeekMusic(Music music) {
        musicDAO.insertWeekMusic(music);
    }

    @Override
    public ArrayList<Music> selectTopMusic() {
        return musicDAO.selectTopMusic();
    }

    @Override
    public ArrayList<Music> selectTopMusicByGenre(String genre) {
        return musicDAO.selectTopMusicByGenre(genre);
    }

    @Override
    public ArrayList<Music> selectTopMusicByDate(String dateValue) {
        return musicDAO.selectTopMusicByDate(dateValue);
    }

    @Override
    public ArrayList<Music> selectTopMusicByDateAndGenre(String date, String genre) {
        return musicDAO.selectTopMusicByDateAndGenre(date, genre);
    }

}
