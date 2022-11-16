package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface Playlist_ManageDAO {

    ArrayList<Playlist_Manage> selectAllPM(int user_number);

    void insertPlaylist_Manage(@Param("list_name") String list_name, @Param("user_number") int user_number);

    User PMgetUserNumByEmail(String user_email);


    ArrayList<Music> selectAllMusic2();

    //플레이리스트 삭제
    void deletePlaylistByUser_number(int user_number);

    //플레이리스트manage 삭제
    void deletePlaylistManageByList_name(String list_name);

    Playlist_Manage samePlaylistNameManage(@Param("list_name") String list_name, @Param("user_number") int user_number);


}
