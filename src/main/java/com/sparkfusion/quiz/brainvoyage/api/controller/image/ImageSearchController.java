package com.sparkfusion.quiz.brainvoyage.api.controller.image;

import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayImageDto;
import com.sparkfusion.quiz.brainvoyage.api.service.ImageSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Search for images",
            description = "Searches images based on the query provided."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Images retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
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
