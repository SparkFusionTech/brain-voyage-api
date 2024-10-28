package com.sparkfusion.quiz.brainvoyage.api.entity.quiz;

import com.sparkfusion.quiz.brainvoyage.api.entity.utils.EntityUtils;
import jakarta.persistence.*;

@Entity
@Table(name = EntityUtils.ANSWER_TABLE, uniqueConstraints = @UniqueConstraint(columnNames = {"number", "question_id"}))
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 24)
    private String name;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quiz_question"))
    private QuestionEntity question;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}