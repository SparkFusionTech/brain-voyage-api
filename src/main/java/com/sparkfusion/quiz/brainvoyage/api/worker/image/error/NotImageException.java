package com.sparkfusion.quiz.brainvoyage.api.worker.image.error;

public class NotImageException extends RuntimeException {

    public NotImageException() {
        super("Image expected");
    }
}
