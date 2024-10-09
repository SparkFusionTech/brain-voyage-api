package com.sparkfusion.quiz.brainvoyage.api.controller;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.AddUserDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserInfoDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.user.UserExistsDto;
import com.sparkfusion.quiz.brainvoyage.api.jwt.JwtResponse;
import com.sparkfusion.quiz.brainvoyage.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/info")
    public ResponseEntity<GetUserInfoDto> readUserInfo(@RequestHeader("Authorization") String authorization) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        GetUserInfoDto userInfoDto = userService.readUserInfo(email);
        return ResponseEntity.ok(userInfoDto);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/check-token")
    public ResponseEntity<Map<String, String>> checkToken(@RequestHeader("Authorization") String authorizationHeader) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Token is valid");
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<UserExistsDto> checkUserExisting(
            @Valid
            @Email
            @RequestParam("email")
            String email
    ) {
        UserExistsDto userExistsDto = userService.isUserExists(email);
        return new ResponseEntity<>(userExistsDto, HttpStatus.OK);
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

    @PostMapping("/create")
    public ResponseEntity<AddUserDto> createUser(
            @Valid
            @NotBlank(message = "Email must not be blank")
            @Email(message = "Email should be valid")
            @RequestPart("email")
            String email,

            @Valid
            @NotBlank(message = "Password must not be blank")
            @Size(min = 8, message = "Password must be at least 8 characters long")
            @RequestPart("password")
            String password,

            @Nullable
            @RequestPart("accountIcon")
            MultipartFile accountIcon
    ) {
        AddUserDto savedUser = userService.registerUser(email, password, accountIcon);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
