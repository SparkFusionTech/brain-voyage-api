package com.sparkfusion.quiz.brainvoyage.api.dto.answer;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;

public class GetAnswerDto {

    private Long id;
    private String name;
    private Integer number;
    private Boolean isCorrect;
    private QuestionEntity question;

    public GetAnswerDto(Long id, String name, Integer number, Boolean isCorrect, QuestionEntity question) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.isCorrect = isCorrect;
        this.question = question;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
