package com.sparkfusion.quiz.brainvoyage.api.controller.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.progress.GetCatalogProgressDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.correct_answer.CorrectAnswer;
import com.sparkfusion.quiz.brainvoyage.api.service.catalog_progress.CatalogProgressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog/progress")
public class CatalogProgressController {

    private final CatalogProgressService catalogProgressService;

    public CatalogProgressController(CatalogProgressService catalogProgressService) {
        this.catalogProgressService = catalogProgressService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<GetCatalogProgressDto> readCatalogProgress(
            @RequestParam("quizId") Long quizId
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(
                catalogProgressService.readCatalogProgressByUserEmailAndQuizId(email, quizId),
                HttpStatus.OK
        );
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/update")
    public ResponseEntity<CorrectAnswer> updateNextTry(
        @RequestParam("quizId") Long quizId
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        catalogProgressService.updateNextTry(email, quizId);
        return new ResponseEntity<>(CorrectAnswer.createSuccessAnswer(), HttpStatus.OK);
    }
}




















