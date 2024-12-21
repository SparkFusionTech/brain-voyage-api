package com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.question_progress;

public class GetCatalogProgressQuestionDto {

    private Long id;
    private Long questionId;
    private Integer xpGained;
    private Boolean correctAnswer;

    public GetCatalogProgressQuestionDto(Long id, Long questionId, Integer xpGained, Boolean correctAnswer) {
        this.id = id;
        this.questionId = questionId;
        this.xpGained = xpGained;
        this.correctAnswer = correctAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getXpGained() {
        return xpGained;
    }

    public void setXpGained(Integer xpGained) {
        this.xpGained = xpGained;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}














