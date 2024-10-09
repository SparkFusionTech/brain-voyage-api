package com.sparkfusion.quiz.brainvoyage.api.exception;

public final class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User was not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
