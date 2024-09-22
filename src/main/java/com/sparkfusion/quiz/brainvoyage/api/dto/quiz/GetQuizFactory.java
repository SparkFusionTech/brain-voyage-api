package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.QuizEntity;
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
                quiz.getRating(),
                quiz.getType(),
                quiz.getQuestions(),
                quiz.getCreatedAt(),
                getUserFactory.mapToDto(quiz.getUser())
        );
    }
}
