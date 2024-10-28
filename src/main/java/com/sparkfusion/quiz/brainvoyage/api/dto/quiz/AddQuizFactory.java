package com.sparkfusion.quiz.brainvoyage.api.dto.quiz;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog.QuizCatalogEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class AddQuizFactory {

    public QuizEntity mapToEntity(
            AddQuizDto addQuizDto,
            UserEntity user,
            QuizCatalogEntity catalog,
            String imageUrl
    ) {
        QuizEntity quiz = new QuizEntity();
        quiz.setTitle(addQuizDto.getTitle());
        quiz.setDescription(addQuizDto.getDescription());
        quiz.setQuestions(addQuizDto.getQuestions());
        quiz.setCatalog(catalog);
        quiz.setImageUrl(imageUrl);
        quiz.setUser(user);
        return quiz;
    }
}
