package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query("SELECT tag FROM TagEntity tag WHERE tag.quiz.id = :quizId")
    List<TagEntity> readTagsByQuizId(@Param("quizId") Long quizId);

    @Query("DELETE FROM TagEntity tag WHERE tag.name = :name AND tag.quiz.id = :quizId")
    void deleteAllByNameAndQuizId(@Param("name") String name ,@Param("quizId") Long quizId);
}
