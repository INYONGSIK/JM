package com.ucamp.JM.dao;

import com.ucamp.JM.dto.AccumulMusic;
import com.ucamp.JM.dto.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface MusicDAO {

    ArrayList<Music> selectMusic();

    //현호 = 검색하는 서비스 매퍼
    ArrayList<Music> SearchByKeyword(@Param("keyword") String keyword, @Param("genre") String genre);

    //현호 = 음악 상세페이지
    @Select("select * from music where music_number=#{music_number}")
    Music showMusicDetails(int music_number);

    //현호 = 좋아요 누르면 좋아요 증가하기
    @Update("update music set music_like=music_like+1 where music_number=#{music_number}")
    void likeIncrement(int music_number);

    void insertTodayMusic(Music music);

    void insertMusic(Music music);

    void insertAccumulMusic(Music music);


    void updateTodayMusic(Music music);

    void updateAccumulMusic(AccumulMusic music);

    void updateAccumulMusicLikeToZero();

    void insertMonthMusic(AccumulMusic music);

    void insertWeekMusic(Music music);

    void updateWeekMusicLikeToZero();

    //세영 : html에 뿌려주기
    ArrayList<Music> selectTopMusic();

    ArrayList<Music> selectTopMusicByGenre(String genre);

    ArrayList<Music> selectTopMusicByDate(String dateValue);

    ArrayList<Music> selectTopMusicByDateAndGenre(@Param("date") String date, @Param("genre") String genre);

}
