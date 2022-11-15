package com.ucamp.JM.service;

import com.ucamp.JM.dto.User;

import java.util.ArrayList;

public interface AlarmService {
    ArrayList<User> selectFollower(int followee);

    ArrayList<User> selectFollowee(int follower);

    void deleteFollow(int follower, int followee);

    void insertFollow(int follower, int followee);

    int following(int follower, int followee);
}
