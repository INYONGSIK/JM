package com.ucamp.JM.Job.examplejob;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobMapper {
    @Insert("insert into music values (#{music_number},#{music_title}," +
            "#{music_singer},#{music_genre},#{music_release},#{music_image}," +
            "#{music_file},#{music_lyrics},#{music_like})")
    void insert(musicDto musicDto);
}
