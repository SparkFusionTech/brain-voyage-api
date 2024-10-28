package com.sparkfusion.quiz.brainvoyage.api.exception;

public final class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException() {
        super("Quiz was not found");
    }

    public QuizNotFoundException(String message) {
        super(message);
    }
}
