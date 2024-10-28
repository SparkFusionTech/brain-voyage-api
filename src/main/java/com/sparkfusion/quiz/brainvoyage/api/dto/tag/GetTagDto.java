package com.sparkfusion.quiz.brainvoyage.api.dto.tag;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;

public class GetTagDto {

    private Long id;
    private String name;
    private QuizEntity quiz;

    public GetTagDto(Long id, String name, QuizEntity quiz) {
        this.id = id;
        this.name = name;
        this.quiz = quiz;
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

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }
}
