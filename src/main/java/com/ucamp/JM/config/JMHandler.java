package com.ucamp.JM.config;

import com.ucamp.JM.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class JMHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
    private Map<String, Object> userSessionsMap;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //클라이언트-서버 연결
        //String user_email = getUser(session);
        sessions.add(session);
        System.out.println("session" + session);

        //String sener_id = getId(session);
        //userSessionsMap.put(user_email, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
/*
        String user_email = getUser(session);
        String msg = message.getPayload();
        log.info("메세지전송 = {} : {}", user_email, message.getPayload());
        if (StringUtils.isNotEmpty(msg)) {
            String[] strs = msg.split(",");
            for (String str : strs) {
                log.info("str:", str);
            }
        }
    }
*/
        String senderId = session.getId();
        for (WebSocketSession sess : sessions) {
            sess.sendMessage(new TextMessage(senderId + ":" + message.getPayload()));
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        /*String user_email = getUser(session);
        sessions.remove(session);
        userSessionsMap.remove(user_email, session);
        log.info("연결 끊김 : {}", user_email);*/
    }

    private String getUser(WebSocketSession session) {
        //TODO:user_email
        Map<String, Object> httpSession = session.getAttributes();
        User loginUser = (User) httpSession.get("user_email");
        if (loginUser == null) {
            return session.getId();
        } else {
            return loginUser.getUser_email();
        }
    }
}
