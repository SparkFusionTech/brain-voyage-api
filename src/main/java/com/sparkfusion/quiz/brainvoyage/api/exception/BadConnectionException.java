package com.sparkfusion.quiz.brainvoyage.api.exception;

public class BadConnectionException extends RuntimeException {

    public BadConnectionException() {
        super("Connection issues");
    }
}
