package com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogProgressQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogProgressQuestionRepository extends JpaRepository<CatalogProgressQuestion, Long> {

    @Query("SELECT cpq FROM CatalogProgressQuestion cpq WHERE cpq.question.id = :questionId AND cpq.catalogProgress.id = :progressId")
    Optional<CatalogProgressQuestion> find(
            @Param("progressId") Long progressId,
            @Param("questionId") Long questionId
    );
}
