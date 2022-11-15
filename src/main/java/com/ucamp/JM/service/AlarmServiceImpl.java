package com.ucamp.JM.service;

import com.ucamp.JM.dao.AlarmDAO;
import com.ucamp.JM.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final AlarmDAO alarmDAO;

    @Override
    public ArrayList<User> selectFollower(int followee) {
        return alarmDAO.selectFollower(followee);
    }

    @Override
    public ArrayList<User> selectFollowee(int follower) {
        return alarmDAO.selectFollowee(follower);
    }

    @Override
    public void deleteFollow(int follower, int followee) {
        alarmDAO.deleteFollow(follower, followee);
    }

    @Override
    public void insertFollow(int follower, int followee) {
        alarmDAO.insertFollow(follower, followee);
    }
}
