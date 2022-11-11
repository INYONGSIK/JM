package com.ucamp.JM.service;

import com.ucamp.JM.dao.PlaylistDAO;
import com.ucamp.JM.dto.Playlist;
import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService{

    private final PlaylistDAO playlistDAO;

    @Override
    public ArrayList<Playlist> selectPlaylist(int user_number, String list_name) {
        return playlistDAO.selectPlaylist(user_number, list_name);
    }

    @Override
    public void insertPlaylist(Playlist playlist) {
        playlistDAO.insertPlaylist(playlist);
    }

    @Override
    public User PgetUserNumByEmail(String user_email) {
        return playlistDAO.PgetUserNumByEmail(user_email);
    }

    @Override
    public List<String> PgetListNameByUserNum(int user_number) {
        return playlistDAO.PgetListNameByUserNum(user_number);
    }

    @Override
    public ArrayList<Playlist_Manage> selectAllP(int user_number) {
        return playlistDAO.selectAllP(user_number);
    }
}
