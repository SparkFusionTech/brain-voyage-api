package com.sparkfusion.quiz.brainvoyage.api.controller.catalog;

import com.sparkfusion.quiz.brainvoyage.api.dto.quiz_catalog.GetQuizCatalogDto;
import com.sparkfusion.quiz.brainvoyage.api.service.QuizCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class QuizCatalogController {

    private final QuizCatalogService quizCatalogService;

    public QuizCatalogController(QuizCatalogService quizCatalogService) {
        this.quizCatalogService = quizCatalogService;
    }

    @Operation(
            summary = "Get all quiz catalog",
            description = "Retrieve a list of the whole quiz catalog"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of quiz catalog",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetQuizCatalogDto.class)
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
    public ResponseEntity<List<GetQuizCatalogDto>> readQuizCatalog(@RequestHeader("Authorization") String authorizationHeader) {
        List<GetQuizCatalogDto> quizCatalogDtoList = quizCatalogService.readQuizCatalog();
        return ResponseEntity.ok(quizCatalogDtoList);
    }
}
