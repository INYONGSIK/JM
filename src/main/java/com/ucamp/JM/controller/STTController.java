package com.ucamp.JM.controller;

import com.ucamp.JM.service.STTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
public class STTController {

    @Autowired
    private STTService sttService;


    @RequestMapping(value = "/stt", method = RequestMethod.GET)
    public String stt() {


        return "stt";

    }

    @PostMapping(value = "/clovaSTT", produces = "application/text; charset=UTF-8")
    @ResponseBody
    public String clovaSTT() {
        // JSON타입으로 받아옴{"text": "음성 인식 된 값"}
        String result = "";


        //기존에는 파일명을 stt.html의 <input type="file">을 이용해 파일명을 파라미터로 받아왔지만
        //어짜피 ajax에서 파일을 다운로드에 voice.mp3로 저장시키기로 하였기에 기존에 파일 받아오는 과정 다 삭제
        String filePathName = "C:/Users/Admin/Downloadsvoice.mp3";
        File file1 = new File(filePathName);
        System.out.println("경로" + filePathName);

        result = sttService.clovaSpeechToText(filePathName);
        System.out.println("ai 결과 = " + result);

        return result;
    }
}
