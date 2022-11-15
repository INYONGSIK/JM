package com.ucamp.JM.dao;

import com.ucamp.JM.dto.Music;
import com.ucamp.JM.dto.Playlist;
import com.ucamp.JM.dto.Playlist_Manage;
import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PlaylistDAO {
    ArrayList<Playlist> selectPlaylist(@Param("user_number") int user_number, @Param("list_name") String list_name);


    void insertPlaylist(Playlist playlist);

    User PgetUserNumByEmail(String user_email);

    List<String> PgetListNameByUserNum(int user_number);

    ArrayList<Playlist_Manage> selectAllP(int user_number);

    Music selectByMusicNumber(int music_number);

    //음악 삭제
    void deletePlaylistMusic(int music_number);

    Music selectSameMusic(int user_number, String list_name, int music_number);
}
