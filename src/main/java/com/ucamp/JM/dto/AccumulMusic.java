package com.ucamp.JM.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Builder(builderMethodName = "AccumulMusic")
public class AccumulMusic {

    private int music_number;
    private String music_title;
    private String music_singer;
    private String music_genre;
    private String music_release;
    private String music_image;
    private String music_file;
    private String music_lyrics;
    private int music_like;

    private int current_music_number;
    private int current_music_like;

    private int accumul_music_like;

}
