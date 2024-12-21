package com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.xp;

public class GetCatalogExperienceDto {

    private Long id;
    private Integer currentXp;
    private String name;
    private Integer level;

    public GetCatalogExperienceDto(Long id, Integer currentXp, String name, Integer level) {
        this.id = id;
        this.currentXp = currentXp;
        this.name = name;
        this.level = level;
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
