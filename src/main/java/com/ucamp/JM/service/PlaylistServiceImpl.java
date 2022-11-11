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
    public ArrayList<Playlist> selectPlaylist(String list_name, int user_number) {
        return playlistDAO.selectPlaylist(list_name, user_number);
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
}
