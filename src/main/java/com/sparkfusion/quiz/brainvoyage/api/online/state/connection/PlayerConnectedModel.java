package com.sparkfusion.quiz.brainvoyage.api.online.state.connection;

import java.io.Serializable;

public class PlayerConnectedModel implements Serializable {

    private String roomId;
    private OpponentModel opponent;

    public PlayerConnectedModel() {}

    public PlayerConnectedModel(String roomId, OpponentModel opponent) {
        this.roomId = roomId;
        this.opponent = opponent;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public OpponentModel getOpponent() {
        return opponent;
    }

    public void setOpponent(OpponentModel opponent) {
        this.opponent = opponent;
    }
}































