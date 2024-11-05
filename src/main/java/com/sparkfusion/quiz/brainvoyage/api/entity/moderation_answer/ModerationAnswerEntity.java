package com.sparkfusion.quiz.brainvoyage.api.entity.moderation_answer;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.utils.EntityUtils;
import jakarta.persistence.*;

@Entity
@Table(name = EntityUtils.MODERATION_ANSWER_TABLE, uniqueConstraints = @UniqueConstraint(columnNames = {"name", "quiz_id"}))
public class ModerationAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quiz_moderation_answer"))
    private QuizEntity quiz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }
}



















