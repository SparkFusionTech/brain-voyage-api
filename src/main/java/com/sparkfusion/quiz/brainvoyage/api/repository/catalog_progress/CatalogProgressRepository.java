package com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogProgressRepository extends JpaRepository<CatalogProgress, Long> {

    @Query("SELECT cp FROM CatalogProgress cp WHERE cp.user.email = :userEmail AND cp.quiz.id = :quizId")
    Optional<CatalogProgress> findByQuizAndUser(@Param("userEmail") String userEmail, @Param("quizId") Long quizId);
}
