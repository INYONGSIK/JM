package com.ucamp.JM.controller;

import com.ucamp.JM.service.STTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class STTController {

    @Autowired
    private STTService sttService;

    @RequestMapping(value = "/voice", method = RequestMethod.GET)
    public String voice() {

        return "voiceRecord";
    }

    @RequestMapping(value = "/stt", method = RequestMethod.GET)
    public String stt() {

        return "sttResult";

    }

    @PostMapping(value = "/clovaSTT", produces = "application/text; charset=UTF-8")
    @ResponseBody
    public String stt(@RequestParam("uploadFile") MultipartFile file,
                      @RequestParam("language") String language) {
        String result = "";

        try {
            //1. 파일 저장 경로 설정 : 실제 서비스 되는 위치 (프로젝트 외부에 저장)   --이현호
            String uploadPath = "c:/ucamp36/ai";

            //2.원본 파일 이름   --이현호
            String originalFileName = file.getOriginalFilename();

            //3. 파일 생성   --이현호
            String filePathName = uploadPath + originalFileName;
            File file1 = new File(filePathName);
            System.out.println(filePathName);
            //4. 서버로 전송   --이현호
            file.transferTo(file1);

            result = sttService.clovaSpeechToText(filePathName, language);
            System.out.println("ai " + result);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
