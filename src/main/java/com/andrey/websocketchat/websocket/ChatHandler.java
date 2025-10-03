package com.andrey.websocketchat.websocket;

import com.andrey.websocketchat.service.ConnectionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private final ConnectionManager connectionManager = new ConnectionManager();

    // sessionId -> userId
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // В реальном проекте userId должен приходить из аутентификации
        String userId = UUID.randomUUID().toString();
        sessionUserMap.put(session.getId(), userId);

        System.out.println(getRoomIdFromSession(session));

        connectionManager.addSession(userId, session);
        System.out.println("User connected: " + userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = sessionUserMap.remove(session.getId());
        if (userId != null) {
            connectionManager.removeSession(userId, session);
            System.out.println("User disconnected: " + userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = sessionUserMap.get(session.getId());
        if (userId == null) throw new RuntimeException("User not found: " + session.getId());

        // payload — это просто текст от пользователя, можно сделать DTO
        String payload = message.getPayload();

        // Рассылаем сообщение всем активным сессиям этого пользователя (или всей комнате)
        Set<WebSocketSession> sessions = connectionManager.getSessions(userId);
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("User " + userId + ": " + payload));
            }
        }
    }

    private String getRoomIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("chatId=")) {
            return query.substring(7);
        }
        return "default";
    }
}

