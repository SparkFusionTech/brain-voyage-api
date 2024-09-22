package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import jakarta.validation.constraints.*;

public final class AddQuizDto {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Type must not be empty")
    private Integer type;

    @NotNull(message = "Questions count must not be empty")
    @Min(value = 1, message = "Questions count must be at least 1")
    @Max(value = 99, message = "Questions count must not be more than 99")
    private Integer questions;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String userEmail;

    public AddQuizDto(String title, String description, Integer type, Integer questions, String userEmail) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.questions = questions;
        this.userEmail = userEmail;
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

    public @NotNull(message = "Type must not be empty") Integer getType() {
        return type;
    }

    public void setType(
            @NotNull(message = "Type must not be empty")
            Integer type
    ) {
        this.type = type;
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

    public @NotBlank(message = "Email must not be blank") @Email(message = "Email should be valid") String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(
            @NotBlank(message = "Email must not be blank")
            @Email(message = "Email should be valid")
            String userEmail
    ) {
        this.userEmail = userEmail;
    }
}
