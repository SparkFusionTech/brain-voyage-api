package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserDto;

import java.time.LocalDateTime;

public final class GetQuizDto {

    private Long id;

    private String title;

    private String description;

    private Double rating;

    private Integer type;

    private Integer questions;

    private LocalDateTime createdAt;

    private GetUserDto user;

    public GetQuizDto(
            Long id,
            String title,
            String description,
            Double rating,
            Integer type,
            Integer questions,
            LocalDateTime createdAt,
            GetUserDto user
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.type = type;
        this.questions = questions;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getQuestions() {
        return questions;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public GetUserDto getUser() {
        return user;
    }

    public void setUser(GetUserDto user) {
        this.user = user;
    }
}
