package com.ucamp.JM.service;

import net.nurigo.java_sdk.api.GroupMessage;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class SendAuthNumberPhone {
    public void certifiedPhoneNumber(String user_phone_number, int randomNumber) {
        String api_key = "NCSPV8YZMCKSQUYI";
        String api_secret = "NCONVJRYQOCGNWXZGDIXVEAMAMSYMC4W";
        GroupMessage coolsms = new GroupMessage(api_key, api_secret);

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("to", user_phone_number); // 수신번호
        params.put("from", "01027256166");   // 발신번호
        params.put("type", "SMS");           // Message type ( SMS, LMS, MMS, ATA )
        params.put("text", "[JM]인증번호는" + "[" + randomNumber + "]" + "입니다.");  // 문자내용

        try {
            JSONObject obj = (JSONObject) coolsms.addMessages(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
