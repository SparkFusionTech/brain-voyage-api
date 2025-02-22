package com.sparkfusion.quiz.brainvoyage.api.online.command;

public enum Events {

    JOIN_OR_CREATE("JOIN_OR_CREATE"),
    DISCONNECT("DISCONNECT"),
    ANSWER_ON_QUESTION("ANSWER_ON_QUESTION"),
    NEXT_QUESTION("NEXT_QUESTION");

    public final String name;

    Events(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}





























