package com.ucamp.JM.service;

import com.ucamp.JM.dao.Playlist_ManageDAO;
import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class Playlist_ManageServiceImpl implements Playlist_ManageService{

    private final Playlist_ManageDAO playlist_manageDAO;

    @Override
    public ArrayList<Playlist_Manage> selectAllPM(int user_number) {
        return playlist_manageDAO.selectAllPM(user_number);
    }

    @Override
    public void insertPlaylist_Manage(Playlist_Manage playlist_manage) {
        playlist_manageDAO.insertPlaylist_Manage(playlist_manage);
    }



    @Override
    public User PMgetUserNumByEmail(String user_email) {
        return playlist_manageDAO.PMgetUserNumByEmail(user_email);
    }

    @Override
    public ArrayList<Music> selectAllMusic2() {
        return playlist_manageDAO.selectAllMusic2();
    }
}
