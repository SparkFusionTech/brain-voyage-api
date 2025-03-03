package com.sparkfusion.quiz.brainvoyage.api.online.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfusion.quiz.brainvoyage.api.online.command.Response;
import com.sparkfusion.quiz.brainvoyage.api.online.command.Responses;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GameMessageSender {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> void sendResponse(
            @NotNull WebSocketSession session,
            Responses response,
            T data
    ) {
        try {
            Response<T> generatedResponse = new Response<>(response.getCode(), response.getMessage(), data);
            String jsonResponse = objectMapper.writeValueAsString(generatedResponse);

            System.out.println(jsonResponse);
            session.sendMessage(new TextMessage(jsonResponse));
        } catch (Exception e) {
            System.out.println("Error sending response: " + e.getMessage());
        }
    }
}


























