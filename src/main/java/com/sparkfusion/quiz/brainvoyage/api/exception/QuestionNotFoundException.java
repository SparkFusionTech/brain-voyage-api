package com.sparkfusion.quiz.brainvoyage.api.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException() {
        super("Question was not found");
    }
}
