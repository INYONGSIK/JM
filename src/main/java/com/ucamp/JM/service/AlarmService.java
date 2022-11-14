package com.ucamp.JM.service;

import com.ucamp.JM.dto.User;

import java.util.ArrayList;

public interface AlarmService {
    ArrayList<User> selectFollower(int followee);

    ArrayList<User> selectFollowee(int follower);

    void deleteFollowee(int follower, int followee);
}
