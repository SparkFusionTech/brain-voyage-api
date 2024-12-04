package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    @Query("SELECT quiz FROM QuizEntity quiz WHERE quiz.user.id = :userId AND quiz.id = :quizId")
    Optional<QuizEntity> findQuizByUserIdAndQuizId(@Param("userId") Long userId, @Param("quizId") Long quizId);

    @Query("SELECT quiz FROM QuizEntity quiz WHERE quiz.catalog.id = :catalogId AND quiz.type = 2")
    List<QuizEntity> findAllByType(@Param("catalogId") Long catalogId);

    @Query("SELECT quiz FROM QuizEntity quiz WHERE quiz.user.email = :userEmail ORDER BY quiz.createdAt")
    List<QuizEntity> findAllByUserEmail(@Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM QuizEntity WHERE id = :id")
    int deleteQuizById(@Param("id") Long quizId);
}
