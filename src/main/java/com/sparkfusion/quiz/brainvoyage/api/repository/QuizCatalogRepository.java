package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog.QuizCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizCatalogRepository extends JpaRepository<QuizCatalogEntity, Long> {
}
