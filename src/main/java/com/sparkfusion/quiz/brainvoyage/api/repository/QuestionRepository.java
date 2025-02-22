package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT question FROM QuestionEntity question WHERE question.quiz.id = :quizId")
    List<QuestionEntity> readQuestionByQuizId(@Param("quizId") Long quizId);

    @Query(value = "SELECT * FROM questions ORDER BY RANDOM() LIMIT 2", nativeQuery = true)
    List<QuestionEntity> findRandomQuestions();

    @Query(value = """
                SELECT q\s
                FROM QuestionEntity q\s
                WHERE q.quiz.id IN (
                    SELECT qc.id\s
                    FROM QuizEntity quiz\s
                    JOIN quiz.catalog qcat\s
                    WHERE qcat.id = :catalogId\s
                )\s
                ORDER BY RANDOM()\s
                LIMIT 2
            """, nativeQuery = true)
    List<QuestionEntity> findRandomQuestions(@Param("catalogId") Long catalogId);
}































