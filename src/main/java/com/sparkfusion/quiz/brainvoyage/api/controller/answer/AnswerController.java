package com.sparkfusion.quiz.brainvoyage.api.controller.answer;

import com.sparkfusion.quiz.brainvoyage.api.dto.answer.GetAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quizzes/questions/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Operation(
            summary = "Get answers by question ID",
            description = "Returns a list of answers associated with a specific question."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Answers successfully retrieved.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GetAnswerDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "An error occurred on the server.")
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<GetAnswerDto>> readAnswersByQuestionId(
            @RequestParam("questionId") Long questionId
    ) {
        List<GetAnswerDto> answers = answerService.readAnswerByQuestionId(questionId);
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
}

















