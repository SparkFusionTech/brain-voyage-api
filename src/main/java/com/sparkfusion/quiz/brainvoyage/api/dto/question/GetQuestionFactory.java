package com.sparkfusion.quiz.brainvoyage.api.dto.question;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import org.springframework.stereotype.Component;

@Component
public class GetQuestionFactory {

    public GetQuestionDto mapToDto(QuestionEntity question) {
        return new GetQuestionDto(
                question.getId(),
                question.getName(),
                question.getImageUrl(),
                question.getCategory(),
                question.getDifficulty(),
                question.getExplanation(),
                question.getQuiz()
        );
    }
}
