package com.sparkfusion.quiz.brainvoyage.api.dto.answer;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.AnswerEntity;
import org.springframework.stereotype.Component;

@Component
public class GetAnswerFactory {

    public GetAnswerDto mapToDto(AnswerEntity answer) {
        return new GetAnswerDto(
                answer.getId(),
                answer.getName(),
                answer.getNumber(),
                answer.isCorrect()
        );
    }
}
