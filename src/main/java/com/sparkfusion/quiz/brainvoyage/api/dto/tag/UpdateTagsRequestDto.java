package com.sparkfusion.quiz.brainvoyage.api.dto.tag;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UpdateTagsRequestDto {

    @NotNull(message = "Tags list must not be null")
    private List<String> tags;

    public UpdateTagsRequestDto(List<String> tags) {
        this.tags = tags;
    }

    public @NotNull(message = "Tags list must not be null") List<String> getTags() {
        return tags;
    }

    public void setTags(@NotNull(message = "Tags list must not be null") List<String> tags) {
        this.tags = tags;
    }
}
