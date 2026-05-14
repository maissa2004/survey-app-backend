package com.example.appenquetes1.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Dans SocketIOConfig.java - Configuration complète
@Configuration
public class SocketIOConfig {
    @Bean(destroyMethod = "stop")
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(8082);
        config.setAllowCustomRequests(true);
        SocketIOServer server = new SocketIOServer(config);
        server.start(); // démarrage immédiat
        return server;
    }
}

