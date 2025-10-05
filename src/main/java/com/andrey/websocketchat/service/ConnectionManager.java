package com.andrey.websocketchat.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConnectionManager {

    // userId -> Set<WebSocketSession>
    private final Map<String, Set<WebSocketSession>> sessionsByUserId = new ConcurrentHashMap<>();

    public void addSession(String userId, WebSocketSession session) {
        sessionsByUserId
                .computeIfAbsent(userId, id -> ConcurrentHashMap.newKeySet())
                .add(session);
    }

    public void removeSession(String userId, WebSocketSession session) {
        Set<WebSocketSession> sessions = sessionsByUserId.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                sessionsByUserId.remove(userId);
            }
        }
    }

    public Set<WebSocketSession> getSessions(String userId) {
        return sessionsByUserId.getOrDefault(userId, Collections.emptySet());
    }

    public boolean isUserOnline(String userId) {
        Set<WebSocketSession> sessions = sessionsByUserId.get(userId);
        return sessions != null && !sessions.isEmpty();
    }

    public int getOnlineUserCount() {
        return sessionsByUserId.size();
    }
}
