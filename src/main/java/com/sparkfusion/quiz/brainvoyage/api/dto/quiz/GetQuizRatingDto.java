package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import java.io.Serializable;

public class GetQuizRatingDto implements Serializable {

    private Double rating;

    public GetQuizRatingDto() {
    }

    public GetQuizRatingDto(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}























