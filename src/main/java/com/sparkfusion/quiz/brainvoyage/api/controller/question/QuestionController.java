package com.sparkfusion.quiz.brainvoyage.api.controller.question;

import com.sparkfusion.quiz.brainvoyage.api.dto.question.AddQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.question.GetQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
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
    @PostMapping("/create")
    public ResponseEntity<GetQuestionDto> createQuestion(AddQuestionDto addQuestionDto) {
        GetQuestionDto getQuestionDto = questionService.addQuestion(addQuestionDto);
        return new ResponseEntity<>(getQuestionDto, HttpStatus.CREATED);
    }
}



















