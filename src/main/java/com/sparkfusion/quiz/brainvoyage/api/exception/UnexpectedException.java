package com.sparkfusion.quiz.brainvoyage.api.exception;

public final class UnexpectedException extends RuntimeException {

    public UnexpectedException() {
        super("Unexpected server error");
    }

    public UnexpectedException(String message) {
        super(message);
    }
}
