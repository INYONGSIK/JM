package com.ucamp.JM.service;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface Playlist_ManageService {

    ArrayList<Playlist_Manage> selectAllPM(int user_number);
    void insertPlaylist_Manage( String list_name,int user_number);

    User PMgetUserNumByEmail(String user_email);

    ArrayList<Music> selectAllMusic2();

    //플레이리스트 삭제
    void deletePlaylistByUser_number(int user_number);

    //플레이리스트manage 삭제
    void deletePlaylistManageByList_name(String list_name);

    Playlist_Manage samePlaylistNameManage(String list_name, int user_number);


}
