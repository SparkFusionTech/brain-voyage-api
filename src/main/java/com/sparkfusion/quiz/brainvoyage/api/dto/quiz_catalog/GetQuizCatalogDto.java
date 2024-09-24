package com.sparkfusion.quiz.brainvoyage.api.dto.quiz_catalog;

public class GetQuizCatalogDto {

    private Long id;
    private String name;
    private String imageUrl;
    private String startGradientColor;
    private String endGradientColor;

    public GetQuizCatalogDto(Long id, String name, String imageUrl, String startGradientColor, String endGradientColor) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.startGradientColor = startGradientColor;
        this.endGradientColor = endGradientColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
