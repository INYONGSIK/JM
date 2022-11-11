package com.ucamp.JM.service;

import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;

import java.util.ArrayList;

public interface Playlist_ManageService {

    ArrayList<Playlist_Manage> selectAllPM();
    void insertPlaylist_Manage(Playlist_Manage playlist_manage);

    User PMgetUserNumByEmail(String user_email);


}
