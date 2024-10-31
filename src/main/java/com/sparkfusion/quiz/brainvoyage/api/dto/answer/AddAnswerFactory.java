package com.sparkfusion.quiz.brainvoyage.api.dto.answer;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.AnswerEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import org.springframework.stereotype.Component;

@Component
public class AddAnswerFactory {

    public AnswerEntity mapToEntity(AddAnswerDto addAnswerDto, QuestionEntity question) {
        AnswerEntity answer = new AnswerEntity();
        answer.setName(addAnswerDto.getName());
        answer.setCorrect(addAnswerDto.getIsCorrect());
        answer.setNumber(addAnswerDto.getNumber());
        answer.setQuestion(question);
        return answer;
    }
}
