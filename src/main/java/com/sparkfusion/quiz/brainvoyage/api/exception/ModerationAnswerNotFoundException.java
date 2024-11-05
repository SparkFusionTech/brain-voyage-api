package com.sparkfusion.quiz.brainvoyage.api.exception;

public class ModerationAnswerNotFoundException extends RuntimeException {

    public ModerationAnswerNotFoundException() {
        super("Moderation answer was not found");
    }
}
