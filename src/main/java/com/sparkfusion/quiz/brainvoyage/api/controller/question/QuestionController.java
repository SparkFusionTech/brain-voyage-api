package com.sparkfusion.quiz.brainvoyage.api.controller.question;

import com.sparkfusion.quiz.brainvoyage.api.dto.question.AddQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.question.GetQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/quizzes/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Operation(
            summary = "Get questions by quiz ID",
            description = "Returns a list of questions associated with a specific quiz."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Questions successfully retrieved.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GetQuestionDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "An error occurred on the server.")
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<GetQuestionDto>> readQuestionsByQuizId(
            @RequestParam("quizId") Long quizId
    ) {
        List<GetQuestionDto> questions = questionService.readQuestionsByQuizId(quizId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new question",
            description = "Allows you to create a new question associated with a quiz."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Question successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetQuestionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Quiz not found"),
                    @ApiResponse(responseCode = "500", description = "An error occurred on the server")
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<GetQuestionDto> createQuestion(
            @Valid
            @NotNull
            @RequestBody
            AddQuestionDto addQuestionDto,

            @NotNull
            @RequestPart("questionImage")
            MultipartFile questionImage
    ) {
        GetQuestionDto getQuestionDto = questionService.addQuestion(addQuestionDto, questionImage);
        return new ResponseEntity<>(getQuestionDto, HttpStatus.CREATED);
    }
}



















