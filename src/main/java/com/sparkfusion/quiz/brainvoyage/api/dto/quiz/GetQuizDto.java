package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserDto;

import java.time.LocalDateTime;

public final class GetQuizDto {

    private Long id;

    private String title;

    private String description;

    private Long catalogType;

    private Double rating;

    private Integer status;

    private String imageUrl;

    private Integer questions;

    private LocalDateTime createdAt;

    private GetUserDto user;

    public GetQuizDto(
            Long id,
            String title,
            String description,
            Long catalogType,
            Double rating,
            Integer status,
            String imageUrl,
            Integer questions,
            LocalDateTime createdAt,
            GetUserDto user
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.catalogType = catalogType;
        this.rating = rating;
        this.status = status;
        this.imageUrl = imageUrl;
        this.questions = questions;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(Long catalogType) {
        this.catalogType = catalogType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
