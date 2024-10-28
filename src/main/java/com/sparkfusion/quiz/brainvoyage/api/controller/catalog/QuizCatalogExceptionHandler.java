package com.sparkfusion.quiz.brainvoyage.api.controller.catalog;

import com.sparkfusion.quiz.brainvoyage.api.exception.QuizCatalogNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception_handler.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuizCatalogExceptionHandler {

    @ExceptionHandler(QuizCatalogNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleQuizCatalogNotFoundException(QuizCatalogNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
