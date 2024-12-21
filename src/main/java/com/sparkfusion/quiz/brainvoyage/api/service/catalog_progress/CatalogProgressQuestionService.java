package com.sparkfusion.quiz.brainvoyage.api.service.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.question_progress.GetCatalogProgressQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.question_progress.GetCatalogProgressQuestionFactory;
import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.question_progress.UpdateCatalogProgressQuestionDto;
import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogProgress;
import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogProgressQuestion;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuestionNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.catalog_xp.CatalogProgressNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.repository.*;
import com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress.CatalogProgressQuestionRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress.CatalogProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogProgressQuestionService {

    private final CatalogProgressQuestionRepository catalogProgressQuestionRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final CatalogProgressRepository catalogProgressRepository;
    private final QuestionRepository questionRepository;

    private final GetCatalogProgressQuestionFactory getCatalogProgressQuestionFactory;

    public CatalogProgressQuestionService(
            CatalogProgressQuestionRepository catalogProgressQuestionRepository,
            UserRepository userRepository,
            QuizRepository quizRepository,
            CatalogProgressRepository catalogProgressRepository,
            QuestionRepository questionRepository,
            GetCatalogProgressQuestionFactory getCatalogProgressQuestionFactory
    ) {
        this.catalogProgressQuestionRepository = catalogProgressQuestionRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.catalogProgressRepository = catalogProgressRepository;
        this.questionRepository = questionRepository;
        this.getCatalogProgressQuestionFactory = getCatalogProgressQuestionFactory;
    }

    @Transactional
    public GetCatalogProgressQuestionDto readCatalogProgressQuestion(
            String userEmail,
            Long quizId,
            Long questionId
    ) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
            if (optionalUser.isEmpty()) throw new UserNotFoundException();

            Optional<QuizEntity> optionalQuiz = quizRepository.findById(quizId);
            if (optionalQuiz.isEmpty()) throw new QuizNotFoundException();

            Optional<CatalogProgress> optionalCatalogProgress = catalogProgressRepository.findByQuizAndUser(userEmail, quizId);
            if (optionalCatalogProgress.isEmpty()) throw new CatalogProgressNotFoundException();

            Optional<QuestionEntity> optionalQuestion = questionRepository.findById(questionId);
            if (optionalQuestion.isEmpty()) throw new QuestionNotFoundException();

            Optional<CatalogProgressQuestion> optionalCatalogProgressQuestion = catalogProgressQuestionRepository.find(
                    optionalCatalogProgress.get().getId(), questionId
            );
            CatalogProgressQuestion catalogProgressQuestion = optionalCatalogProgressQuestion.orElseGet(
                    () -> {
                        CatalogProgressQuestion newCatalogProgressQuestion = new CatalogProgressQuestion();
                        newCatalogProgressQuestion.setCorrectAnswer(false);
                        newCatalogProgressQuestion.setXpGained(0);
                        newCatalogProgressQuestion.setCatalogProgress(optionalCatalogProgress.get());
                        newCatalogProgressQuestion.setQuestion(optionalQuestion.get());

                        return catalogProgressQuestionRepository.save(newCatalogProgressQuestion);
                    }
            );

            return getCatalogProgressQuestionFactory.mapToDto(catalogProgressQuestion);
        } catch (UserNotFoundException | QuizNotFoundException | CatalogProgressNotFoundException |
                 QuestionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public void updateCatalogProgressQuestion(
            String userEmail,
            UpdateCatalogProgressQuestionDto updateCatalogProgressQuestionDto
    ) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
            if (optionalUser.isEmpty()) throw new UserNotFoundException();

            Optional<QuizEntity> optionalQuiz = quizRepository.findById(updateCatalogProgressQuestionDto.getQuizId());
            if (optionalQuiz.isEmpty()) throw new QuizNotFoundException();

            Optional<CatalogProgress> optionalCatalogProgress = catalogProgressRepository.findByQuizAndUser(userEmail, updateCatalogProgressQuestionDto.getQuizId());
            if (optionalCatalogProgress.isEmpty()) throw new CatalogProgressNotFoundException();

            Optional<QuestionEntity> optionalQuestion = questionRepository.findById(updateCatalogProgressQuestionDto.getQuestionId());
            if (optionalQuestion.isEmpty()) throw new QuestionNotFoundException();

            Optional<CatalogProgressQuestion> optionalCatalogProgressQuestion = catalogProgressQuestionRepository.find(
                    optionalCatalogProgress.get().getId(), updateCatalogProgressQuestionDto.getQuestionId()
            );
            if (optionalCatalogProgressQuestion.isEmpty()) {
                CatalogProgressQuestion catalogProgressQuestion = new CatalogProgressQuestion();
                catalogProgressQuestion.setCorrectAnswer(updateCatalogProgressQuestionDto.getCorrectAnswer());
                catalogProgressQuestion.setXpGained(updateCatalogProgressQuestionDto.getXpGained());
                catalogProgressQuestion.setCatalogProgress(optionalCatalogProgress.get());
                catalogProgressQuestion.setQuestion(optionalQuestion.get());

                catalogProgressQuestionRepository.save(catalogProgressQuestion);
            } else {
                CatalogProgressQuestion catalogProgressQuestion = optionalCatalogProgressQuestion.get();
                if (!catalogProgressQuestion.getCorrectAnswer()) {
                    catalogProgressQuestion.setXpGained(updateCatalogProgressQuestionDto.getXpGained());
                    catalogProgressQuestion.setCorrectAnswer(updateCatalogProgressQuestionDto.getCorrectAnswer());

                    catalogProgressQuestionRepository.save(catalogProgressQuestion);
                }
            }
        } catch (UserNotFoundException | QuizNotFoundException | CatalogProgressNotFoundException |
                 QuestionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

}



















