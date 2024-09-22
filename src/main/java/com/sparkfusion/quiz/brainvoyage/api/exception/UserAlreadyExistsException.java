package com.sparkfusion.quiz.brainvoyage.api.exception;

public final class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
