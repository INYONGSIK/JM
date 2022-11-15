package com.ucamp.JM.service;


import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class STTServiceImpl implements STTService {


    @Override
    public String clovaSpeechToText(String filePathName) {

        String clientId = "xol4i19ny6";             // Application Client ID"     --이현호;
        String clientSecret = "giPyiivYOd5AzbId0qhA3vvrCdGWHth2dOWGrz6J";     // Application Client Secret"   --이현호;
        String result = null;
        try {
            File voiceFile = new File(filePathName);

            String language = "Kor";        // 언어 코드 ( Kor, Jpn, Eng, Chn )    --이현호
            String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            String errorMessage = conn.getResponseMessage();
            if (responseCode == 200) { // 정상 호출   --이현호
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생   --이현호
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                System.out.println("error!!!!!!! responsemessage= " + errorMessage);

                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine;

            if (br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                result = response.toString();
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }
}
