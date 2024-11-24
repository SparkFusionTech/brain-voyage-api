package com.sparkfusion.quiz.brainvoyage.api.exception.storage;

public class FailedStorageConnectionException extends RuntimeException {
    public FailedStorageConnectionException() {
        super("Failed storage connection");
    }
}
