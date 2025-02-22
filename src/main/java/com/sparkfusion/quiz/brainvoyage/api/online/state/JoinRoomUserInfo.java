package com.sparkfusion.quiz.brainvoyage.api.online.state;

import jakarta.annotation.Nullable;

import java.io.Serializable;

public class JoinRoomUserInfo implements Serializable {

    private String email;

    @Nullable
    private Long catalogId;

    public JoinRoomUserInfo() {}

    public JoinRoomUserInfo(String email, @Nullable Long catalogId) {
        this.email = email;
        this.catalogId = catalogId;
    }

    @Nullable
    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(@Nullable Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

























