package com.ucamp.JM.dao;

import com.ucamp.JM.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface AlarmDAO {
    ArrayList<User> selectFollower(int followee);

    ArrayList<User> selectFollowee(int follower);

    void deleteFollow(@Param("follower") int follower, @Param("followee") int followee);

    void insertFollow(@Param("follower") int follower, @Param("followee") int followee);

    int following(@Param("follower") int follower, @Param("followee") int followee);

}
