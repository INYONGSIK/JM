package com.ucamp.JM.config;


import com.ucamp.JM.dao.UserDAO;
import com.ucamp.JM.dto.User;
import com.ucamp.JM.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SignalHandler extends TextWebSocketHandler {
    private final HttpSession httpSession;
    private final AlarmService alarmService;
    private final UserDAO userDAO;
    // 테스트용 : 전체유저에게 매세지보낼 리스트 => 접속되어있는 모든 세션을 담음
    List<WebSocketSession> sessions = new ArrayList<>();
    // 각 사용자에 맞는 값을얻기위해 map 을 사용
    Map<String, WebSocketSession> userSessions = new HashMap<>();

    // 브라우저가 WebSocket 과의 핸드쉐이크를 완료하고 연결/세션을 생성 할 때마다 호출됨
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("afterConnectionEstablished : " + session);
        sessions.add(session);
        String senderId = getId(session);
        System.out.println("senderId : " + senderId + " session : " + session);
        userSessions.put(senderId, session);
        System.out.println("keySet : " + userSessions.keySet());
    }

    // 특정 세션이 webSocket 에 메세지를 보낼 때마다 호출 됨
    // 이런 일이 발생하면 webSocket 에 연결된 모든 세션을 반복하고 각 세션에 메시지를 보냄
    // 이떄 메세지를 보낸 세션은 제외 (자신에게 메시지를 보내는 것을 방지하기 위해)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        //모든 유저에게 보내는 부분
        /*for (WebSocketSession sess : sessions) {
            sess.sendMessage(new TextMessage(message.getPayload()));  //getPayload 실제 보낼 메세지
        }
*/
        // protocol : cmd , 댓글작성자, 게시글작성자, bno (reply, user2 , user1, 234)  bno => 게시글번호
        String msg = message.getPayload();
        if (StringUtils.isNotEmpty(msg)) {
            String[] strs = msg.split(",");
            if (strs != null && strs.length == 2) {
                //cmd, uploader
                String cmd = strs[0];
                int sender = Integer.parseInt(strs[1]);

                ArrayList<User> followers = alarmService.selectFollower(sender);
                for (User follower : followers) {
                    WebSocketSession follower_session = null;
                    for (Map.Entry<String, WebSocketSession> entry : userSessions.entrySet()) {
                        if (entry.getKey().equals(String.valueOf(follower.getUser_number()))) {
                            follower_session = entry.getValue();
                        }
                    }
                    System.out.println("follower_session : " + follower_session);
                    if (cmd.equals("upload") && follower_session != null) {
                        TextMessage tmpMsg = new TextMessage(sender + "님이 곡을 업로드 했습니다.");
                        follower_session.sendMessage(tmpMsg);
                    }
                }
            }
            if (strs != null && strs.length == 3) {
                String cmd = strs[0];
                User follower = userDAO.getUserInfoByEmail(strs[1]);
                User followee = userDAO.getUserInfoByUsername(strs[2]);
                String follower_nickname = follower.getUser_nickname();
                WebSocketSession followeeSession = userSessions.get(followee.getUser_name());

                if (cmd.equals("follow") && followeeSession != null) {
                    TextMessage tmpMsg = new TextMessage(follower_nickname + "님이 회원님을 팔로우하기 시작했습니다.");
                    followeeSession.sendMessage(tmpMsg);

                    alarmService.insertFollow(follower.getUser_number(), followee.getUser_number());
                }
            }


            if (strs != null && strs.length == 4) {
                String cmd = strs[0];
                String replyWriter = strs[1];

                String boardWriter = userDAO.getUserInfoByNickname(strs[2]).getUser_name();
                String dashboardNo = strs[3];

                WebSocketSession replyWriterSession = userSessions.get(replyWriter);
                WebSocketSession boardWriterSession = userSessions.get(boardWriter);
                if (cmd.equals("reply") && boardWriterSession != null) {
                    TextMessage tmpMsg = new TextMessage(replyWriter + "님이 "
                            + "<a href=/readboard/" + dashboardNo + ">" + dashboardNo
                            + "</a>번 게시물에 "
                            + "댓글을 달았습니다."
//                            + "<button id=deleteAlert" + i + ">삭제</button>" + "<br><br>"
                    );
                    boardWriterSession.sendMessage(tmpMsg);
                }
            }
        }
    }

    private String getId(WebSocketSession session) {
        Map<String, Object> httpSession = session.getAttributes();
        System.out.println("httpSession : " + httpSession);
        String user_session = String.valueOf(httpSession.get("user_name"));


        // 로그인 했으면 로그인한 유저의 아이디를 주고
        // 로그인 안했으면 소켓의 아이디를 줌
        if (user_session == null) return session.getId();
        else return user_session;
    }

    // 브라우저가 연결을 닫으면 이 메서드가 호출되고 세션이 세션 목록에서 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("afterConnectionClosed" + session + " : " + status);
        sessions.remove(session);
    }
}