package com.ucamp.JM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Music {
    private int music_number;
    private String music_title;
    private String music_singer;
    private String music_genre;
    private Date music_release;
    private String music_image;
    private String music_file;
    private String music_lyrics;
    private int music_like;
}
