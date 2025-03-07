package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import java.io.Serializable;

public class GetUserQuizRatingDto implements Serializable {

    private Long id;
    private Integer rating;
    private GetQuizDto getQuizDto;

    public GetUserQuizRatingDto() {
    }

    public GetUserQuizRatingDto(Long id, Integer rating, GetQuizDto getQuizDto) {
        this.id = id;
        this.rating = rating;
        this.getQuizDto = getQuizDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public GetQuizDto getGetQuizDto() {
        return getQuizDto;
    }

    public void setGetQuizDto(GetQuizDto getQuizDto) {
        this.getQuizDto = getQuizDto;
    }
}





























