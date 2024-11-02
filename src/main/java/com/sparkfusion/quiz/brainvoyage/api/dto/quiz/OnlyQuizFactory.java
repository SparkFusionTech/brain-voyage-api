package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import org.springframework.stereotype.Component;

@Component
public class OnlyQuizFactory {

    public OnlyQuizDto mapToDto(QuizEntity quiz) {
        return new OnlyQuizDto(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getType(),
                quiz.getRating(),
                quiz.getImageUrl(),
                quiz.getQuestions(),
                quiz.getCreatedAt()
        );
    }
}
