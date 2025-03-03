package com.sparkfusion.quiz.brainvoyage.api.online.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfusion.quiz.brainvoyage.api.online.command.Command;
import com.sparkfusion.quiz.brainvoyage.api.online.command.Events;
import com.sparkfusion.quiz.brainvoyage.api.online.command.Responses;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class GameMessageHandler extends TextWebSocketHandler {

    private final CommandHandler commandHandler;
    private final GameMessageSender gameMessageSender;

    public GameMessageHandler(
            CommandHandler commandHandler,
            GameMessageSender gameMessageSender
    ) {
        this.commandHandler = commandHandler;
        this.gameMessageSender = gameMessageSender;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("New connection: " + session.getId());
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Command command = objectMapper.readValue(message.getPayload(), Command.class);
            commandHandler.handleCommand(session, command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            gameMessageSender.sendResponse(session, Responses.UNEXPECTED, null);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NotNull CloseStatus status) {
        Command command = new Command();
        command.setAction(Events.DISCONNECT.getName());
        commandHandler.handleCommand(session, command);
        System.out.println("Status: " + status.getCode() + " Reason: " + status.getReason() + " Connection closed: " + session.getId());
    }
}



































