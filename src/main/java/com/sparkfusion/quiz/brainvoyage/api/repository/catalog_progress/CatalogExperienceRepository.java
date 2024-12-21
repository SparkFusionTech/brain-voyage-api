package com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogExperienceRepository extends JpaRepository<CatalogExperience, Long> {

    @Query("SELECT ce FROM CatalogExperience ce WHERE ce.user.email = :email")
    Optional<CatalogExperience> findByUser(@Param("email") String email);
}
