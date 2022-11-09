package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Playlist_Manage;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface Playlist_ManageDAO {

    ArrayList<Playlist_Manage> selectAllPM();
    void insertPlaylist_Manage(Playlist_Manage playlist_manage);
}