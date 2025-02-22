package com.sparkfusion.quiz.brainvoyage.api.online.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfusion.quiz.brainvoyage.api.online.command.Command;
import com.sparkfusion.quiz.brainvoyage.api.online.command.Events;
import com.sparkfusion.quiz.brainvoyage.api.online.state.AnswerOnQuestionModel;
import com.sparkfusion.quiz.brainvoyage.api.online.state.GameRoom;
import com.sparkfusion.quiz.brainvoyage.api.online.state.JoinRoomUserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommandHandler {

    private final GameRoomHandler gameRoomHandler;

    private final Map<String, GameRoom> gameRooms = new ConcurrentHashMap<>();

    public CommandHandler(
            GameRoomHandler gameRoomHandler
    ) {
        this.gameRoomHandler = gameRoomHandler;
    }

    public void handleCommand(WebSocketSession session, Command command) {
        String action = command.getAction();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Events event = Events.valueOf(action.toUpperCase());
            switch (event) {
                case JOIN_OR_CREATE:
                    System.out.println("Handling JOIN_OR_CREATE event");
                    JoinRoomUserInfo joinRoomUserInfo = objectMapper.convertValue(command.getData(), JoinRoomUserInfo.class);
                    gameRoomHandler.createOrJoinRoom(gameRooms, session, joinRoomUserInfo);
                    break;
                case DISCONNECT:
                    System.out.println("Handling DISCONNECT event");
                    gameRoomHandler.disconnectPlayer(gameRooms, session);
                    break;
                case ANSWER_ON_QUESTION:
                    System.out.println("Handling ANSWER_ON_QUESTION event");
                    AnswerOnQuestionModel answerOnQuestionModel = objectMapper.convertValue(command.getData(), AnswerOnQuestionModel.class);
                    gameRoomHandler.answerOnQuestion(gameRooms, session, answerOnQuestionModel);
                    break;
                case NEXT_QUESTION:
                    System.out.println("Handling NEXT_QUESTION event");
                    gameRoomHandler.nextQuestion(gameRooms, session);
                    break;
                default:
                    System.out.println("Unknown event: " + event);
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command: " + command);
        }
    }
}





























