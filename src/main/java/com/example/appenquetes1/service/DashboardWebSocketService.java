package com.example.appenquetes1.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.appenquetes1.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DashboardWebSocketService {

    @Autowired
    private SocketIOServer socketServer;

    public void emitDashboardUpdate(Object data) {
        socketServer.getRoomOperations("dashboard_room").sendEvent("dashboardUpdate", data);
    }

    public void emitNewSubmission(Object submission) {
        socketServer.getRoomOperations("dashboard_room").sendEvent("newSubmission", submission);
    }

    public void emitNewNotification(Notification notification) {
        socketServer.getRoomOperations("dashboard_room").sendEvent("newNotification", notification);
        socketServer.getRoomOperations("notification_room").sendEvent("newNotification", notification);
    }

    // Optionnel : gérer l'arrivée d'un client admin dans la room
    public void addClientToDashboardRoom(String sessionId) {
        socketServer.getClient(UUID.fromString(sessionId)).joinRoom("dashboard_room");
    }
}