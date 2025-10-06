package com.andrey.websocketchat.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Новое соединение: " + session.getId() + ", всего: " + sessions.size());

        String welcomeMessage = "[Система] Добро пожаловать в чат! Подключено пользователей: " + sessions.size();
        sendMessageToSession(session, welcomeMessage);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String formattedMessage = formatMessage(payload);
        broadcastMessage(formattedMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Ошибка транспорта для сессии " + session.getId() + ": " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
        System.out.println("Соединение закрыто: " + session.getId() + ", причина: " + closeStatus.getReason());

        String userLeftMessage = "[Система] Пользователь отключился. Подключено: " + sessions.size();
        broadcastMessage(userLeftMessage);
    }

    private String formatMessage(String rawMessage) {
        String time = LocalTime.now().format(timeFormatter);
        return "[" + time + "] " + rawMessage;
    }

    private void broadcastMessage(String message) {
        TextMessage textMessage = new TextMessage(message);

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(textMessage);
                } catch (IOException e) {
                    System.err.println("Ошибка отправки сообщения сессии " + session.getId() + ": " + e.getMessage());
                }
            }
        }
    }

    private void sendMessageToSession(WebSocketSession session, String message) {
        TextMessage textMessage = new TextMessage(message);

        if (session.isOpen()) {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                System.err.println("Ошибка отправки сообщения сессии " + session.getId() + ": " + e.getMessage());
            }
        }
    }

}

