package com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.utils.CatalogProgressTables;
import jakarta.persistence.*;

@Entity
@Table(name = CatalogProgressTables.CATALOG_LEVEL)
public class CatalogLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String name;

    @Column(name = "xp_count", nullable = false)
    private Integer xpCount;

    @Column(name = "level", nullable = false, unique = true)
    private Integer level;

    @Column(name = "color", nullable = false)
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Integer getXpCount() {
        return xpCount;
    }

    public void setXpCount(Integer xpCount) {
        this.xpCount = xpCount;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}


















