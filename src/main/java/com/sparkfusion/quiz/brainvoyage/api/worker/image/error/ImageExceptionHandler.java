package com.sparkfusion.quiz.brainvoyage.api.worker.image.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageExceptionHandler {

    @ExceptionHandler(NotImageException.class)
    @ResponseBody
    public ResponseEntity<String> handleNotImageException(NotImageException notImageException) {
        return ResponseEntity.badRequest().body(notImageException.getMessage());
    }
}
