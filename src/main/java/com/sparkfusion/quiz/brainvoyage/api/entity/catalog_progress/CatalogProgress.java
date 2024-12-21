package com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.utils.CatalogProgressTables;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = CatalogProgressTables.CATALOG_PROGRESS,
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "quiz_id"})
)
public class CatalogProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizEntity quiz;

    @Column(name = "play_count", nullable = false)
    private Integer playCount;

    @Column(name = "next_try_at", nullable = false)
    private LocalDateTime nextTryAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public LocalDateTime getNextTryAt() {
        return nextTryAt;
    }

    public void setNextTryAt(LocalDateTime nextTryAt) {
        this.nextTryAt = nextTryAt;
    }
}



















