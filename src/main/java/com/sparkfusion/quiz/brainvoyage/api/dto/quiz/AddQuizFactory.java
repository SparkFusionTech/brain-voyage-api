package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import com.sparkfusion.quiz.brainvoyage.api.entity.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class AddQuizFactory {

    public AddQuizDto mapToDto(QuizEntity quiz) {
        return new AddQuizDto(
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getType(),
                quiz.getQuestions(),
                quiz.getUser().getEmail()
        );
    }

    public QuizEntity mapToEntity(AddQuizDto addQuizDto, UserEntity user) {
        QuizEntity quiz = new QuizEntity();
        quiz.setTitle(addQuizDto.getTitle());
        quiz.setDescription(addQuizDto.getDescription());
        quiz.setQuestions(addQuizDto.getQuestions());
        quiz.setType(addQuizDto.getType());
        quiz.setUser(user);
        return quiz;
    }
}
