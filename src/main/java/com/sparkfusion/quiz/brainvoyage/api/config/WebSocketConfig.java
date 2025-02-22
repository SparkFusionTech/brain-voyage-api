package com.sparkfusion.quiz.brainvoyage.api.config;

import com.sparkfusion.quiz.brainvoyage.api.online.handler.GameMessageHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameMessageHandler gameMessageHandler;

    public WebSocketConfig(GameMessageHandler gameMessageHandler) {
        this.gameMessageHandler = gameMessageHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameMessageHandler, "/quiz/game")
                .setAllowedOrigins("*");
    }
}


























