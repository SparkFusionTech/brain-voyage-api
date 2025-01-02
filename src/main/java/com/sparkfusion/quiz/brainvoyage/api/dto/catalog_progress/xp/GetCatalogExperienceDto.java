package com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.xp;

public class GetCatalogExperienceDto {

    private Long id;
    private Integer currentXp;
    private Integer levelXp;
    private String name;
    private Integer level;
    private String color;

    public GetCatalogExperienceDto(Long id, Integer currentXp, Integer levelXp, String name, Integer level, String color) {
        this.id = id;
        this.currentXp = currentXp;
        this.levelXp = levelXp;
        this.name = name;
        this.level = level;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getLevelXp() {
        return levelXp;
    }

    public void setLevelXp(Integer levelXp) {
        this.levelXp = levelXp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrentXp() {
        return currentXp;
    }

    public void setCurrentXp(Integer currentXp) {
        this.currentXp = currentXp;
    }
}
