package com.sparkfusion.quiz.brainvoyage.api.controller.user;

import com.sparkfusion.quiz.brainvoyage.api.exception.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @Operation(
            summary = "Handle user already exists exception",
            description = "Handle cases where a user already exists"
    )
    @ApiResponse(
            responseCode = "409",
            description = "User already exists",
            content = @Content(mediaType = "application/json")
    )
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
