package com.sparkfusion.quiz.brainvoyage.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = EntityUtils.QUIZ_CATALOG_TABLE)
public class QuizCatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "start_gradient_color", nullable = false)
    private String startGradientColor;

    @Column(name = "end_gradient_color", nullable = false)
    private String endGradientColor;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStartGradientColor() {
        return startGradientColor;
    }

    public void setStartGradientColor(String startGradientColor) {
        this.startGradientColor = startGradientColor;
    }

    public String getEndGradientColor() {
        return endGradientColor;
    }

    public void setEndGradientColor(String endGradientColor) {
        this.endGradientColor = endGradientColor;
    }
}














