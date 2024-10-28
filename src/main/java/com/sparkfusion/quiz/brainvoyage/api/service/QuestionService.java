package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.question.AddQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.question.AddQuestionFactory;
import com.sparkfusion.quiz.brainvoyage.api.dto.question.GetQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.question.GetQuestionFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuestionRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    private final AddQuestionFactory addQuestionFactory;
    private final GetQuestionFactory getQuestionFactory;

    public QuestionService(
            QuestionRepository questionRepository,
            QuizRepository quizRepository,
            AddQuestionFactory addQuestionFactory,
            GetQuestionFactory getQuestionFactory
    ) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.addQuestionFactory = addQuestionFactory;
        this.getQuestionFactory = getQuestionFactory;
    }

    @Transactional
    public GetQuestionDto addQuestion(AddQuestionDto addQuestionDto) {
        try {
            Optional<QuizEntity> quiz = quizRepository.findById(addQuestionDto.getQuizId());
            if (quiz.isEmpty()) {
                throw new QuizNotFoundException();
            }

            QuestionEntity question = questionRepository.save(addQuestionFactory.mapToEntity(addQuestionDto, quiz.get()));
            return getQuestionFactory.mapToDto(question);
        } catch (QuizNotFoundException e) {
            throw e;
        } catch (Exception exception) {
            throw new UnexpectedException();
        }
    }
}






















