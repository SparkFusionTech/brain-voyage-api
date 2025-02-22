package com.sparkfusion.quiz.brainvoyage.api.online.state;

import java.io.Serializable;

public class AnswerOnQuestionModel implements Serializable {

    private Integer score;

    public AnswerOnQuestionModel() {}

    public AnswerOnQuestionModel(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}































