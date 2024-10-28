package com.sparkfusion.quiz.brainvoyage.api.controller.quiz;

import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.AddQuizDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.GetQuizDto;
import com.sparkfusion.quiz.brainvoyage.api.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @Operation(
            summary = "Delete a quiz",
            description = "Delete a quiz by the given id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully quiz deletion",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetQuizDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Incorrect data to have permission to deletion"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found or Quiz not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteQuiz(
            @PathVariable("id") Long id
    ) {
        Boolean deleted = quizService.deleteQuiz(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all quizzes by type",
            description = "Retrieve a list of quizzes filtered by the given type"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of quizzes",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetQuizDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<GetQuizDto>> readAllQuizByType(
            @RequestParam("type") Integer type
    ) {
        List<GetQuizDto> quizzes = quizService.readAllQuizzesByType(type);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new quiz",
            description = "Create a new quiz with the provided details"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Quiz successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetQuizDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input provided"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<GetQuizDto> createQuiz(
            @Valid
            @NotNull
            @RequestBody
            AddQuizDto addQuizDto,

            @NotNull
            @RequestPart("quizImage")
            MultipartFile quizImage
    ) {
        GetQuizDto getQuizDto = quizService.addQuiz(addQuizDto, quizImage);
        return new ResponseEntity<>(getQuizDto, HttpStatus.CREATED);
    }
}




















