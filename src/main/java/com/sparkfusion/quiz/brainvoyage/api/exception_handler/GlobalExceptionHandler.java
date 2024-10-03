package com.sparkfusion.quiz.brainvoyage.api.exception_handler;

import com.sparkfusion.quiz.brainvoyage.api.exception.BadConnectionException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnableExecuteRequestException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(UnableExecuteRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUnableExecuteRequestException(UnableExecuteRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadConnectionException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBadConnectionException(BadConnectionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUnexpectedException(UnexpectedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Handle user not found exception",
            description = "Handle cases where a user is not found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(mediaType = "application/json")
    )
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
