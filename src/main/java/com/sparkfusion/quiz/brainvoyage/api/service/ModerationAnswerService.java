package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.moderation_answer.GetModerationAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.moderation_answer.GetModerationAnswerFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.moderation_answer.ModerationAnswerEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.ModerationAnswerNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.repository.ModerationAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ModerationAnswerService {

    private final ModerationAnswerRepository moderationAnswerRepository;

    private final GetModerationAnswerFactory getModerationAnswerFactory;

    public ModerationAnswerService(
            ModerationAnswerRepository moderationAnswerRepository,
            GetModerationAnswerFactory getModerationAnswerFactory
    ) {
        this.moderationAnswerRepository = moderationAnswerRepository;
        this.getModerationAnswerFactory = getModerationAnswerFactory;
    }

    @Transactional(readOnly = true)
    public GetModerationAnswerDto readModerationAnswerByQuestionId(
            Long quizId
    ) {
        try {
            Optional<ModerationAnswerEntity> moderationAnswer = moderationAnswerRepository.findModerationAnswerByQuestionId(quizId);
            if (moderationAnswer.isEmpty()) {
                throw new ModerationAnswerNotFoundException();
            }

            return getModerationAnswerFactory.matToDto(moderationAnswer.get());
        } catch (ModerationAnswerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public void clearModerationAnswersOfQuestion(Long quizId) {
        try {
            moderationAnswerRepository.deleteAll();
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }
}



































