package com.sparkfusion.quiz.brainvoyage.api.dto.question;

import com.sparkfusion.quiz.brainvoyage.api.dto.answer.GetAnswerDto;

import java.util.List;

public class GetQuestionWithAnswerDto {

    private Long id;
    private String name;
    private String imageUrl;
    private Integer category;
    private Integer difficulty;
    private String explanation;
    private List<GetAnswerDto> answers;

    public GetQuestionWithAnswerDto(
            Long id,
            String name,
            String imageUrl,
            Integer category,
            Integer difficulty,
            String explanation,
            List<GetAnswerDto> answers
    ) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.difficulty = difficulty;
        this.explanation = explanation;
        this.answers = answers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<GetAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<GetAnswerDto> answers) {
        this.answers = answers;
    }
}
