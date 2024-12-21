package com.sparkfusion.quiz.brainvoyage.api.dto.correct_answer;

public class CorrectAnswer {

    private String status;

    public CorrectAnswer(String status) {
        this.status = status;
    }

    public static CorrectAnswer createSuccessAnswer() {
        return new CorrectAnswer("Success");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
