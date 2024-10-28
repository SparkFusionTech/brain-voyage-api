package com.sparkfusion.quiz.brainvoyage.api.exception;

public class QuizCatalogNotFoundException extends RuntimeException {

    public QuizCatalogNotFoundException() {
        super("Quiz catalog was not found");
    }
}
