package com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.question_progress;

public class UpdateCatalogProgressQuestionDto {

    private Long quizId;
    private Long questionId;
    private Integer xpGained;
    private Boolean correctAnswer;

    public UpdateCatalogProgressQuestionDto(Long quizId, Long questionId, Integer xpGained, Boolean correctAnswer) {
        this.quizId = quizId;
        this.questionId = questionId;
        this.xpGained = xpGained;
        this.correctAnswer = correctAnswer;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
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
