package com.sparkfusion.quiz.brainvoyage.api.jwt;

public class JwnTokenExpiredException extends RuntimeException {

    public JwnTokenExpiredException(String message) {
        super(message);
    }
}
