package com.sparkfusion.quiz.brainvoyage.api.controller;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.AddUserDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserDto;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserAlreadyExistsException;
import com.sparkfusion.quiz.brainvoyage.api.jwt.JwtResponse;
import com.sparkfusion.quiz.brainvoyage.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "User authentication",
            description = "Check user email and password correctness and return JWT token"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully user authentication",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class) // Возвращаем токен
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @PostMapping("/authentication")
    public ResponseEntity<JwtResponse> authenticateUser(
            @Valid
            @RequestBody
            AddUserDto getUserDto
    ) {
        JwtResponse token = userService.loginAndGenerateToken(getUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/check-token")
    public ResponseEntity<String> checkToken(@RequestHeader("Authorization") String authorizationHeader) {
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }

    @Operation(
            summary = "Check user existence",
            description = "Check is user exists"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully user existence check",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Boolean.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUserExisting(
            @Valid
            @Email
            @RequestParam("email")
            String email
    ) {
        Boolean exists = userService.isEmailFree(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all users"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of users",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddUserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<GetUserDto>> getAllUsers() {
        List<GetUserDto> users = userService.readAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new user",
            description = "Create a new user with the provided details"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddUserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input provided"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User with the given email already exists"
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<AddUserDto> createUser(
            @Valid
            @RequestBody
            AddUserDto addUserDTO
    ) {
        AddUserDto savedUser = userService.addUser(addUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
