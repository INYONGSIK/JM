package com.ucamp.JM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyMusic {

    private String user_number;
    private String music_title;
    private String list_name;
    private String playlist_cd;
    private int music_number;
    private String music_release;
    private String music_image;
    private String music_singer;
    private String music_genre;
    private String music_file;
    private String music_lyrics;
}
