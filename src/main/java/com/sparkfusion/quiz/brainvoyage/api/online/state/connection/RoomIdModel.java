package com.sparkfusion.quiz.brainvoyage.api.online.state.connection;

import java.io.Serializable;

public class RoomIdModel implements Serializable {

    private String roomId;

    public RoomIdModel() {}

    public RoomIdModel(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
























