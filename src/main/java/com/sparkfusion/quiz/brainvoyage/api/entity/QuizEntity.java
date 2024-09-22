package com.sparkfusion.quiz.brainvoyage.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = EntityUtils.QUIZ_TABLE)
public final class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rating", nullable = false)
    private Double rating = 0.0;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "questions", nullable = false)
    private Integer questions;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public @NotBlank(message = "Title must not be blank") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title must not be blank") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Description must not be blank") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description must not be blank") String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public @NotNull(message = "Type must not be empty") Integer getType() {
        return type;
    }

    public void setType(@NotNull(message = "Type must not be empty") Integer type) {
        this.type = type;
    }

    public @NotNull(message = "Questions count must not be empty") Integer getQuestions() {
        return questions;
    }

    public void setQuestions(@NotNull(message = "Questions count must not be empty") Integer questions) {
        this.questions = questions;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
