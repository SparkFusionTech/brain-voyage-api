package com.sparkfusion.quiz.brainvoyage.api.online.state;

import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;

public class Player implements Serializable {

    private WebSocketSession session;

    private Long id;
    private String name;
    private String iconUrl;
    private Boolean connected = true;
    private Integer score = 0;
    private Boolean answered = false;
    private Boolean clickNext = false;

    public Player() {}

    public Player(WebSocketSession session, Long id, String name, String iconUrl) {
        this.session = session;
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public void addScore(Integer score) {
        this.score += score;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public Boolean getClickNext() {
        return clickNext;
    }

    public void setClickNext(Boolean clickNext) {
        this.clickNext = clickNext;
    }
}


























