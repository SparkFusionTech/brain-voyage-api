package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class AddQuizDto {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotNull(message = "Catalog must not be null")
    private Long catalogId;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Questions count must not be empty")
    @Min(value = 1, message = "Questions count must be at least 1")
    @Max(value = 50, message = "Questions count must not be more than 50")
    private Integer questions;

    public AddQuizDto() {}

    public AddQuizDto(String title, Long catalogId, String description, Integer questions) {
        this.title = title;
        this.catalogId = catalogId;
        this.description = description;
        this.questions = questions;
    }

    public @NotNull(message = "Catalog must not be null") Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(@NotNull(message = "Catalog must not be null") Long catalogId) {
        this.catalogId = catalogId;
    }

    public @NotBlank(message = "Title must not be blank") String getTitle() {
        return title;
    }

    public void setTitle(
            @NotBlank(message = "Title must not be blank")
            String title
    ) {
        this.title = title;
    }

    public @NotBlank(message = "Description must not be blank") String getDescription() {
        return description;
    }

    public void setDescription(
            @NotBlank(message = "Description must not be blank")
            String description
    ) {
        this.description = description;
    }

    public @NotNull(message = "Questions count must not be empty") Integer getQuestions() {
        return questions;
    }

    public void setQuestions(
            @NotNull(message = "Questions count must not be empty")
            Integer questions
    ) {
        this.questions = questions;
    }
}
