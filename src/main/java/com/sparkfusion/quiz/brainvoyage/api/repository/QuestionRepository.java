package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT question FROM QuestionEntity question WHERE question.quiz.id = :quizId")
    List<QuestionEntity> readQuestionByQuizId(@Param("quizId") Long quizId);
}
