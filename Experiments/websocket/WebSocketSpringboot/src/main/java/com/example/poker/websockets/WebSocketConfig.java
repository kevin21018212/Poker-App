package com.example.poker.websockets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Define prefixes for message topics
        config.enableSimpleBroker("/lobby", "/game");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket endpoints for connecting to the lobby
        registry.addEndpoint("/lobby-websocket")
                .setAllowedOrigins("*")
                .withSockJS();

        // WebSocket endpoints for connecting to the game
        registry.addEndpoint("/game-websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
