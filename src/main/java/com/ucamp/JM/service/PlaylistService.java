package com.ucamp.JM.service;

import com.ucamp.JM.dto.Playlist;
import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;

import java.util.ArrayList;
import java.util.List;

public interface PlaylistService {


    ArrayList<Playlist> selectPlaylist();

    void insertPlaylist(Playlist playlist);

    User PgetUserNumByEmail(String user_email);

    List<String> PgetListNameByUserNum(int user_number);
}
