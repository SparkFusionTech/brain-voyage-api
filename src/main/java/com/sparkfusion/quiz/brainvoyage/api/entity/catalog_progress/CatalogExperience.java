package com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.utils.CatalogProgressTables;
import jakarta.persistence.*;

@Entity
@Table(name = CatalogProgressTables.CATALOG_EXPERIENCE)
public class CatalogExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_xp", nullable = false)
    private Integer currentXp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_level_id", nullable = false)
    private CatalogLevel catalogLevel;

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CatalogLevel getCatalogLevel() {
        return catalogLevel;
    }

    public void setCatalogLevel(CatalogLevel catalogLevel) {
        this.catalogLevel = catalogLevel;
    }
}





















