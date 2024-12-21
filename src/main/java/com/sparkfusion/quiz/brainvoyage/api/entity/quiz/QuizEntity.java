package com.sparkfusion.quiz.brainvoyage.api.entity.quiz;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog.QuizCatalogEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.utils.EntityUtils;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = EntityUtils.QUIZ_TABLE)
public final class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @Column(name = "type", nullable = false)
    private Integer type = 0;

    @Column(name = "questions", nullable = false)
    private Integer questions;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "rating", nullable = false, columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
    private Double rating = 0.0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quiz_user"))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quiz_catalog"))
    private QuizCatalogEntity catalog;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public QuizCatalogEntity getCatalog() {
        return catalog;
    }

    public void setCatalog(QuizCatalogEntity catalog) {
        this.catalog = catalog;
    }
}