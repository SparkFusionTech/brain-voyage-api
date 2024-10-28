package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import org.springframework.stereotype.Component;

@Component
public final class GetQuizFactory {

    private final GetUserFactory getUserFactory;

    public GetQuizFactory(GetUserFactory getUserFactory) {
        this.getUserFactory = getUserFactory;
    }

    public GetQuizDto mapToDto(QuizEntity quiz) {
        return new GetQuizDto(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getCatalog().getId(),
                quiz.getRating(),
                quiz.getType(),
                quiz.getImageUrl(),
                quiz.getQuestions(),
                quiz.getCreatedAt(),
                getUserFactory.mapToDto(quiz.getUser())
        );
    }
}
