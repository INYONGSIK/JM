package com.ucamp.JM.service;

import com.ucamp.JM.dao.Playlist_ManageDAO;
import com.ucamp.JM.dto.Playlist_Manage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class Playlist_ManageServiceImpl implements Playlist_ManageService{

    private final Playlist_ManageDAO playlist_manageDAO;

    @Override
    public ArrayList<Playlist_Manage> selectAllPM() {
        return playlist_manageDAO.selectAllPM();
    }

    @Override
    public void insertPlaylist_Manage(Playlist_Manage playlist_manage) {
        playlist_manageDAO.insertPlaylist_Manage(playlist_manage);
    }
}
