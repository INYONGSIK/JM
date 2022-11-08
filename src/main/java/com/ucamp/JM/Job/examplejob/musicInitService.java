package com.ucamp.JM.Job.examplejob;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class musicInitService {
    @Autowired
    JobMapper jobMapper;

    @Transactional(rollbackFor = Exception.class)
    public void musicInit(musicDto music) {
        jobMapper.insert(music);
    }
}
