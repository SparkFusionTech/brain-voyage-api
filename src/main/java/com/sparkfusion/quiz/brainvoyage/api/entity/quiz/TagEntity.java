package com.sparkfusion.quiz.brainvoyage.api.entity.quiz;

import com.sparkfusion.quiz.brainvoyage.api.entity.utils.EntityUtils;
import jakarta.persistence.*;

@Entity
@Table(name = EntityUtils.TAG_TABLE, uniqueConstraints = @UniqueConstraint(columnNames = {"name", "quiz_id"}))
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tag_quiz"))
    private QuizEntity quiz;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }
}