package com.ucamp.JM.service.main;

import com.ucamp.JM.dao.MainDAO;
import com.ucamp.JM.dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MainServiceImpl implements MainService {


    @Autowired
    MainDAO mainDAO;

    @Override
    public ArrayList<Board> mainBoardSelectTop10() {
        return mainDAO.mainBoardSelectTop10();
    }
}
