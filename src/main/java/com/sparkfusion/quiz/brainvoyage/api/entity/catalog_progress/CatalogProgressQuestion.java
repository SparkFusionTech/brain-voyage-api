package com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.utils.CatalogProgressTables;
import jakarta.persistence.*;

@Entity
@Table(name = CatalogProgressTables.CATALOG_PROGRESS_QUESTION)
public class CatalogProgressQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_progress_id", nullable = false)
    private CatalogProgress catalogProgress;

    @Column(name = "xp_gained", nullable = false)
    private Integer xpGained;

    @Column(name = "correct_answer", nullable = false)
    private Boolean correctAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public CatalogProgress getCatalogProgress() {
        return catalogProgress;
    }

    public void setCatalogProgress(CatalogProgress catalogProgress) {
        this.catalogProgress = catalogProgress;
    }

    public Integer getXpGained() {
        return xpGained;
    }

    public void setXpGained(Integer xpGained) {
        this.xpGained = xpGained;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}





















