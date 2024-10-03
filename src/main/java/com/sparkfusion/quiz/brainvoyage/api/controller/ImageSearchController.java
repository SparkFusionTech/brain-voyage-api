package com.sparkfusion.quiz.brainvoyage.api.controller;

import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayImageDto;
import com.sparkfusion.quiz.brainvoyage.api.service.ImageSearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageSearchController {

    private final ImageSearchService imageSearchService;

    public ImageSearchController(ImageSearchService imageSearchService) {
        this.imageSearchService = imageSearchService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/search")
    public ResponseEntity<List<PixabayImageDto>> searchImages(
            @RequestHeader("Authorization") String authorizationHeader,

            @Valid
            @NotNull
            @NotEmpty
            @RequestParam String query
    ) {
        List<PixabayImageDto> images = imageSearchService.searchImages(query);
        return ResponseEntity.ok(images);
    }
}
