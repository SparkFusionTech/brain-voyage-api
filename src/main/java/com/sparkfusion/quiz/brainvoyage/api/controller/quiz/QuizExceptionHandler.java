package com.sparkfusion.quiz.brainvoyage.api.controller.quiz;

import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuizExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleQuizNotFoundException(QuizNotFoundException quizNotFoundException) {
        return new ResponseEntity<>(quizNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
