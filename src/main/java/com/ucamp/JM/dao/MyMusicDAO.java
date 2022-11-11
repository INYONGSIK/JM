package com.ucamp.JM.dao;

import com.ucamp.JM.dto.MyMusic;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MyMusicDAO {

    ArrayList<MyMusic> selectMyMusic();

    void insert(MyMusic myMusic);

    void delete(int MyMusic_number);
}
