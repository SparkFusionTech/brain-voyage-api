package com.sparkfusion.quiz.brainvoyage.api.dto.moderation_answer;

import com.sparkfusion.quiz.brainvoyage.api.entity.moderation_answer.ModerationAnswerEntity;

public class GetModerationAnswerFactory {

    public GetModerationAnswerDto matToDto(ModerationAnswerEntity moderationAnswer) {
        return new GetModerationAnswerDto(
                moderationAnswer.getId(),
                moderationAnswer.getName()
        );
    }
}
