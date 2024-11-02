package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import java.time.LocalDateTime;

public class OnlyQuizDto {

    private Long id;

    private String title;

    private String description;

    private Integer status;

    private Double rating;

    private String imageUrl;

    private Integer questions;

    private LocalDateTime createdAt;

    public OnlyQuizDto(
            Long id,
            String title,
            String description,
            Integer status,
            Double rating,
            String imageUrl,
            Integer questions,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.questions = questions;
        this.createdAt = createdAt;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
