package com.sparkfusion.quiz.brainvoyage.api.dto.tag;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AddTagsRequestDto {

    @NotNull(message = "Tags list must not be null")
    private List<String> tags;

    @NotNull(message = "Quiz id must not be null")
    private Long quizId;

    public AddTagsRequestDto(List<String> tags, Long quizId) {
        this.tags = tags;
        this.quizId = quizId;
    }

    public @NotNull(message = "Tags list must not be null") List<String> getTags() {
        return tags;
    }

    public void setTags(@NotNull(message = "Tags list must not be null") List<String> tags) {
        this.tags = tags;
    }

    public @NotNull(message = "Quiz id must not be null") Long getQuizId() {
        return quizId;
    }

    public void setQuizId(@NotNull(message = "Quiz id must not be null") Long quizId) {
        this.quizId = quizId;
    }
}
