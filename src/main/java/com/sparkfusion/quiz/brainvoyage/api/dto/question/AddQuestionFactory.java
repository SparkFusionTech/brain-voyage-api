package com.sparkfusion.quiz.brainvoyage.api.dto.question;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import org.springframework.stereotype.Component;

@Component
public class AddQuestionFactory {

    public QuestionEntity mapToEntity(AddQuestionDto addQuestionDto, QuizEntity quiz, String imageUrl) {
        QuestionEntity question = new QuestionEntity();
        question.setName(addQuestionDto.getName());
        question.setImageUrl(imageUrl);
        question.setCategory(addQuestionDto.getCategory());
        question.setDifficulty(addQuestionDto.getDifficulty());
        question.setExplanation(addQuestionDto.getExplanation());
        question.setQuiz(quiz);
        return question;
    }
}
