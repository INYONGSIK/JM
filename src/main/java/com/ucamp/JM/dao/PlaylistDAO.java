package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Playlist;
import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PlaylistDAO {
    ArrayList<Playlist> selectPlaylist(String list_name, int user_number);


    void insertPlaylist(Playlist playlist);

    User PgetUserNumByEmail(String user_email);

    List<String> PgetListNameByUserNum(int user_number);

    ArrayList<Playlist_Manage> selectAllP(int user_number);
}
