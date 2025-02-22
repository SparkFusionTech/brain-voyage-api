package com.sparkfusion.quiz.brainvoyage.api.online.command;

public enum Responses {

    ROOM_CREATED(100, "ROOM_CREATED", "Room was created successfully"),
    PLAYER_CONNECTED(101, "PLAYER_CONNECTED", "Player was connected to the room"),
    OPPONENT_DISCONNECTED(102, "OPPONENT_DISCONNECTED", "Opponent was disconnected and game was finished"),
    DISCONNECTED(103, "DISCONNECTED", "Player was disconnected"),

    GAME_START(200, "GAME_START", "The game is starting"),
    GAME_END(201, "GAME_END", "The game is finished"),
    NEXT_QUESTION(202, "NEXT_QUESTION", "The next question of the game"),
    WAITING_OPPONENT_ANSWER(203, "WAITING_OPPONENT_ANSWER", "Wait for the opponent answer"),
    WAITING_NEXT_QUESTION(204, "WAITING_NEXT_QUESTION", "Wait for the next question generation"),

    UNEXPECTED(400, "UNEXPECTED", "Unexpected exception has occurred"),
    PLAYER_IN_GAME(401, "PLAYER_IN_GAME", "Player already in game"),
    USER_NOT_FOUND(402, "USER_NOT_FOUND", "User was not found"),
    PLAYER_NOT_IN_GAME(403, "PLAYER_NOT_IN_GAME", "Player is not in game yet"),
    GAME_ERROR(404, "GAME_ERROR", "Game cannot start"),

    FAILURE_PLAYER_CONNECTION(500, "FAILURE_PLAYER_CONNECTION", "Failed connect player to a game"),
    ANSWER_TIMEOUT(501, "ANSWER_TIMEOUT", "Answer time is finished");

    private final Integer code;
    private final String name;
    private final String message;

    Responses(Integer code, String name, String message) {
        this.code = code;
        this.name = name;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}





























