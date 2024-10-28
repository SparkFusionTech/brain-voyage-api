package com.sparkfusion.quiz.brainvoyage.api.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddTagDto {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Quiz id must not be null")
    private Long quizId;

    public AddTagDto(String name, Long quizId) {
        this.name = name;
        this.quizId = quizId;
    }

    public @NotBlank(message = "Name must not be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name must not be blank") String name) {
        this.name = name;
    }

    public @NotNull(message = "Quiz id must not be null") Long getQuizId() {
        return quizId;
    }

    public void setQuizId(@NotNull(message = "Quiz id must not be null") Long quizId) {
        this.quizId = quizId;
    }
}
