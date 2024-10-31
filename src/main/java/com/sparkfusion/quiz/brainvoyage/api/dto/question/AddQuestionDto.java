package com.sparkfusion.quiz.brainvoyage.api.dto.question;

import com.sparkfusion.quiz.brainvoyage.api.dto.answer.AddAnswerDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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

    @NotNull(message = "Answers must not be null")
    private List<AddAnswerDto> answers;

    public AddQuestionDto() {
    }

    public AddQuestionDto(
            String name,
            Integer category,
            Integer difficulty,
            String explanation,
            Long quizId,
            List<AddAnswerDto> answers
    ) {
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.explanation = explanation;
        this.quizId = quizId;
        this.answers = answers;
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

    public @NotNull(message = "Answers must not be null") List<AddAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(@NotNull(message = "Answers must not be null") List<AddAnswerDto> answers) {
        this.answers = answers;
    }
}