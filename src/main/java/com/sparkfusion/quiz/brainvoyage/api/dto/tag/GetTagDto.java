package com.sparkfusion.quiz.brainvoyage.api.dto.tag;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;

public class GetTagDto {

    private String name;
    private QuizEntity quiz;

    public GetTagDto(String name, QuizEntity quiz) {
        this.name = name;
        this.quiz = quiz;
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
