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

    private int MyMusic_number;
    private String MyMusic_title;
    private String MyMusic_singer;
    private String MyMusic_genre;
    private String MyMusic_release;
    private String MyMusic_image;
    private String MyMusic_file;
    private String MyMusic_lyrics;
    private int MyMusic_like;

}
