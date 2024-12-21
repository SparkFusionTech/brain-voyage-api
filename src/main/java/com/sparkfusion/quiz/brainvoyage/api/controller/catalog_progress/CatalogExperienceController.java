package com.sparkfusion.quiz.brainvoyage.api.controller.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.xp.GetCatalogExperienceDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.correct_answer.CorrectAnswer;
import com.sparkfusion.quiz.brainvoyage.api.service.catalog_progress.CatalogExperienceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog/experience")
public class CatalogExperienceController {

    private final CatalogExperienceService catalogExperienceService;

    public CatalogExperienceController(CatalogExperienceService catalogExperienceService) {
        this.catalogExperienceService = catalogExperienceService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<GetCatalogExperienceDto> readCurrentCatalogExperience() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(catalogExperienceService.readCurrentCatalogExperience(email), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/update")
    public ResponseEntity<CorrectAnswer> updateCatalogExperience(
            @RequestParam("addXp") Integer addXp
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        catalogExperienceService.updateCatalogExperience(email, addXp);
        return new ResponseEntity<>(CorrectAnswer.createSuccessAnswer(), HttpStatus.OK);
    }
}


















