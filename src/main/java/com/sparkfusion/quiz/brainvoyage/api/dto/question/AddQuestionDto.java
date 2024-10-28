package com.sparkfusion.quiz.brainvoyage.api.dto.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddQuestionDto {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Category must not be null")
    private Integer category;

    @NotNull(message = "Difficulty must not be null")
    private Integer difficulty;

    private String explanation;

    @NotNull(message = "Quiz must not be null")
    private Long quizId;

    public AddQuestionDto(String name, Integer category, Integer difficulty, String explanation, Long quizId) {
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.explanation = explanation;
        this.quizId = quizId;
    }

    public @NotBlank(message = "Name must not be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name must not be blank") String name) {
        this.name = name;
    }

    public @NotNull(message = "Category must not be null") Integer getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Category must not be null") Integer category) {
        this.category = category;
    }

    public @NotNull(message = "Difficulty must not be null") Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(@NotNull(message = "Difficulty must not be null") Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public @NotNull(message = "Quiz must not be null") Long getQuizId() {
        return quizId;
    }

    public void setQuizId(@NotNull(message = "Quiz must not be null") Long quizId) {
        this.quizId = quizId;
    }
}
