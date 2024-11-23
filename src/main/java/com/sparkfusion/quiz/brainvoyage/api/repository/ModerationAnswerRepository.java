package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.entity.moderation_answer.ModerationAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ModerationAnswerRepository extends JpaRepository<ModerationAnswerEntity, Long> {

    @Query("SELECT modAnswer FROM ModerationAnswerEntity modAnswer WHERE modAnswer.quiz.id = :quizId")
    Optional<ModerationAnswerEntity> findModerationAnswerByQuestionId(@Param("quizId") Long quizId);
}
