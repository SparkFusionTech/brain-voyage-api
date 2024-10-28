package com.sparkfusion.quiz.brainvoyage.api.controller.question;

import com.sparkfusion.quiz.brainvoyage.api.exception.QuestionNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception_handler.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuestionExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleQuestionNotFoundException(QuestionNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
