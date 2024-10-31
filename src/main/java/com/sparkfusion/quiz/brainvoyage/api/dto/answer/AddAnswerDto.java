package com.sparkfusion.quiz.brainvoyage.api.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddAnswerDto {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Number must not be null")
    private Integer number;

    private Boolean isCorrect;

    public AddAnswerDto() {
    }

    public AddAnswerDto(String name, Integer number, Boolean isCorrect) {
        this.name = name;
        this.number = number;
        this.isCorrect = isCorrect;
    }

    public @NotBlank(message = "Name must not be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name must not be blank") String name) {
        this.name = name;
    }

    public @NotNull(message = "Number must not be null") Integer getNumber() {
        return number;
    }

    public void setNumber(@NotNull(message = "Number must not be null") Integer number) {
        this.number = number;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
