package com.sparkfusion.quiz.brainvoyage.api.dto.quiz_catalog;

import com.sparkfusion.quiz.brainvoyage.api.entity.QuizCatalogEntity;
import org.springframework.stereotype.Component;

@Component
public class GetQuizCatalogFactory {

    public GetQuizCatalogDto mapToDto(QuizCatalogEntity quizCatalogEntity) {
        return new GetQuizCatalogDto(
                quizCatalogEntity.getId(),
                quizCatalogEntity.getName(),
                quizCatalogEntity.getImageUrl(),
                quizCatalogEntity.getStartGradientColor(),
                quizCatalogEntity.getEndGradientColor()
        );
    }
}
