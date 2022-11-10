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


    @RequestMapping(value = "/stt", method = RequestMethod.GET)
    public String stt() {


        return "stt";

    }

    @PostMapping(value = "/clovaSTT", produces = "application/text; charset=UTF-8")
    @ResponseBody
    public String stt(@RequestParam("uploadFile") MultipartFile file) {
        String result = "";

        try {
            //1. 파일 저장 경로 설정 : 실제 서비스 되는 위치 (프로젝트 외부에 저장)   --이현호
            String uploadPath = "C:/Users/Admin/Downloads";


            //2.원본 파일 이름   --이현호
            String originalFileName = file.getOriginalFilename();

            //3. 파일 생성   --이현호
            String filePathName = uploadPath + originalFileName;
            File file1 = new File(filePathName);
            System.out.println("경로" + filePathName);
            //4. 서버로 전송   --이현호
            file.transferTo(file1);

            result = sttService.clovaSpeechToText(filePathName);
            System.out.println("ai 결과 = " + result);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
