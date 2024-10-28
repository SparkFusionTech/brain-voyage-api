package com.sparkfusion.quiz.brainvoyage.api.controller.answer;

import com.sparkfusion.quiz.brainvoyage.api.dto.answer.AddAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.answer.GetAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.service.AnswerService;
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
@RequestMapping("/quizzes/questions/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Operation(
            summary = "Create a new answer",
            description = "Allows you to create a new answer associated with a question."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Answer successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetAnswerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Question not found"),
                    @ApiResponse(responseCode = "500", description = "An error occurred on the server")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<GetAnswerDto> createAnswer(AddAnswerDto addAnswerDto) {
        GetAnswerDto getAnswerDto = answerService.addAnswer(addAnswerDto);
        return new ResponseEntity<>(getAnswerDto, HttpStatus.CREATED);
    }
}

















