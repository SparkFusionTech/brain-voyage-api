package com.sparkfusion.quiz.brainvoyage.api.controller.tag;

import com.sparkfusion.quiz.brainvoyage.api.dto.tag.AddTagDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.GetTagDto;
import com.sparkfusion.quiz.brainvoyage.api.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Operation(
            summary = "Create a new tag",
            description = "Allows you to create a new tag associated with a quiz."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Tag successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetTagDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Quiz not found"),
                    @ApiResponse(responseCode = "500", description = "An error occurred on the server")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<GetTagDto> createTag(
            @Valid
            @RequestBody
            AddTagDto addTagDto
    ) {
        GetTagDto getTagDto = tagService.addTag(addTagDto);
        return new ResponseEntity<>(getTagDto, HttpStatus.CREATED);
    }
}















