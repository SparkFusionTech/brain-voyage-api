package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRatingRepository extends JpaRepository<QuizRatingEntity, Long> {

    @Query("SELECT q FROM QuizRatingEntity q WHERE q.user.id = :userId AND q.quiz.id = :quizId")
    Optional<QuizRatingEntity> findQuizRatingByUserId(
            @Param("userId") Long userId,
            @Param("quizId") Long quizId
    );

    @Query("SELECT AVG(q.rating) FROM QuizRatingEntity q WHERE q.quiz.id = :quizId")
    Double findAverageRatingByQuizId(@Param("quizId") Long quizId);
}




























