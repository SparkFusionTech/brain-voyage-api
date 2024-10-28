package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    @Query("SELECT answer FROM AnswerEntity answer WHERE answer.question.id = :questionId")
    List<AnswerEntity> readAnswersByQuestionId(@Param("questionId") Long questionId);
}
