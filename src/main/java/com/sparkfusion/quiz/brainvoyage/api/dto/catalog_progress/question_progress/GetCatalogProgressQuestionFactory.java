package com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.question_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogProgressQuestion;
import org.springframework.stereotype.Component;

@Component
public class GetCatalogProgressQuestionFactory {

    public GetCatalogProgressQuestionDto mapToDto(CatalogProgressQuestion catalogProgressQuestion) {
        return new GetCatalogProgressQuestionDto(
                catalogProgressQuestion.getId(),
                catalogProgressQuestion.getQuestion().getId(),
                catalogProgressQuestion.getXpGained(),
                catalogProgressQuestion.getCorrectAnswer()
        );
    }
}
