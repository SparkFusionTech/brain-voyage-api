package com.sparkfusion.quiz.brainvoyage.api.exception;

public class DataParsingException extends RuntimeException {

    public DataParsingException() {
        super("Failed request parsing");
    }
}
