package com.sparkfusion.quiz.brainvoyage.api.controller.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.question_progress.GetCatalogProgressQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.question_progress.UpdateCatalogProgressQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.correct_answer.CorrectAnswer;
import com.sparkfusion.quiz.brainvoyage.api.service.catalog_progress.CatalogProgressQuestionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("catalog/progress/question")
public class CatalogProgressQuestionController {

    private final CatalogProgressQuestionService catalogProgressQuestionService;

    public CatalogProgressQuestionController(CatalogProgressQuestionService catalogProgressQuestionService) {
        this.catalogProgressQuestionService = catalogProgressQuestionService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<GetCatalogProgressQuestionDto> readCatalogProgressQuestion(
            @RequestParam("quizId") Long quizId,
            @RequestParam("questionId") Long questionId
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(
                catalogProgressQuestionService.readCatalogProgressQuestion(email, quizId, questionId),
                HttpStatus.OK
        );
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/update")
    public ResponseEntity<CorrectAnswer> updateCatalogProgressQuestion(
            @RequestBody UpdateCatalogProgressQuestionDto updateCatalogProgressQuestionDto
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        catalogProgressQuestionService.updateCatalogProgressQuestion(email, updateCatalogProgressQuestionDto);
        return new ResponseEntity<>(CorrectAnswer.createSuccessAnswer(), HttpStatus.OK);
    }
}



















