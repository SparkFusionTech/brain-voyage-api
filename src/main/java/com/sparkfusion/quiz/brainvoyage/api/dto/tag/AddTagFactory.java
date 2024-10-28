package com.sparkfusion.quiz.brainvoyage.api.dto.tag;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.TagEntity;
import org.springframework.stereotype.Component;

@Component
public class AddTagFactory {

    public TagEntity mapToEntity(AddTagDto addTagDto, QuizEntity quiz) {
        TagEntity tag = new TagEntity();
        tag.setName(addTagDto.getName());
        tag.setQuiz(quiz);
        return tag;
    }
}
