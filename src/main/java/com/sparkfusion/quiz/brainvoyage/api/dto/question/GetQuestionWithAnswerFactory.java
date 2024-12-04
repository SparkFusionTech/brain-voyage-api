package com.sparkfusion.quiz.brainvoyage.api.dto.question;

import com.sparkfusion.quiz.brainvoyage.api.dto.answer.GetAnswerFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.AnswerEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetQuestionWithAnswerFactory {

    private final GetAnswerFactory getAnswerFactory;

    public GetQuestionWithAnswerFactory(GetAnswerFactory getAnswerFactory) {
        this.getAnswerFactory = getAnswerFactory;
    }

    public GetQuestionWithAnswerDto mapToDto(QuestionEntity question, List<AnswerEntity> answers) {
        return new GetQuestionWithAnswerDto(
                question.getId(),
                question.getName(),
                question.getImageUrl(),
                question.getCategory(),
                question.getDifficulty(),
                question.getExplanation(),
                answers.stream()
                        .map(getAnswerFactory::mapToDto)
                        .toList()
        );
    }
}
















