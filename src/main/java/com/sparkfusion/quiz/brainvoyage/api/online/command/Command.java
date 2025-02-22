package com.sparkfusion.quiz.brainvoyage.api.online.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Command {

    private String action;
    private Object data;

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

    @JsonProperty("data")
    public Object getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Object data) {
        this.data = data;
    }
}



























