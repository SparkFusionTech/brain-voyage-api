package com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogLevelRepository extends JpaRepository<CatalogLevel, Long> {

    @Query("SELECT cl FROM CatalogLevel cl WHERE cl.level = :catalogLevel")
    Optional<CatalogLevel> readCatalogLevel(@Param("catalogLevel") Integer level);
}
