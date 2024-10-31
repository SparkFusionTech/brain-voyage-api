package com.sparkfusion.quiz.brainvoyage.api.controller.tag;

import com.sparkfusion.quiz.brainvoyage.api.dto.tag.AddTagDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.AddTagsRequestDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.GetTagDto;
import com.sparkfusion.quiz.brainvoyage.api.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Operation(
            summary = "Get tags by quiz ID",
            description = "Returns a list of tags associated with a specific quiz."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tags successfully retrieved.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GetTagDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "An error occurred on the server.")
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<GetTagDto>> readTagsByQuizId(
            @RequestParam("quizId") Long quizId
    ) {
        List<GetTagDto> tags = tagService.readTagsByQuizId(quizId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
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
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<GetTagDto> createTag(
            @Valid
            @RequestBody
            AddTagDto addTagDto
    ) {
        GetTagDto getTagDto = tagService.addTag(addTagDto);
        return new ResponseEntity<>(getTagDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create new tags",
            description = "Allows you to create a list of new tags associated with a quiz."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Tags successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GetTagDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Quiz not found"),
                    @ApiResponse(responseCode = "500", description = "An error occurred on the server")
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/createAll")
    public ResponseEntity<Integer> createTags(
            @RequestBody AddTagsRequestDto tagsRequest
    ) {
        Integer count = tagService.addTags(tagsRequest.getTags(), tagsRequest.getQuizId());
        return new ResponseEntity<>(count, HttpStatus.CREATED);
    }
}















