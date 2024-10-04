package com.sparkfusion.quiz.brainvoyage.api.controller;

import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayImageDto;
import com.sparkfusion.quiz.brainvoyage.api.service.ImageSearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
            @Valid @NotNull @NotEmpty @RequestParam(value = "query") String query,
            @Valid @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @Max(value = 200) @Min(value = 3) @RequestParam(value = "per_page", defaultValue = "20") int perPage
    ) {
        List<PixabayImageDto> images = imageSearchService.searchImages(query, page, perPage);
        return ResponseEntity.ok(images);
    }
}
