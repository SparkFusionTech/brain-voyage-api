package com.sparkfusion.quiz.brainvoyage.api.exception_handler;

import com.sparkfusion.quiz.brainvoyage.api.exception.catalog_xp.CatalogExperienceNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.catalog_xp.CatalogProgressNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CatalogProgressExceptionHandler {

    @ExceptionHandler(CatalogExperienceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCatalogExperienceNotFoundException(CatalogExperienceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CatalogProgressNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCatalogProgressNotFoundException(CatalogProgressNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}














