package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.GetQuizFactory;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.GetQuizRatingDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.GetUserQuizRatingDto;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizRatingEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizRatingRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class QuizRatingService {

    private final QuizRatingRepository quizRatingRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    private final GetQuizFactory getQuizFactory;

    public QuizRatingService(
            QuizRatingRepository quizRatingRepository,
            UserRepository userRepository,
            QuizRepository quizRepository,
            GetQuizFactory getQuizFactory
    ) {
        this.quizRatingRepository = quizRatingRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.getQuizFactory = getQuizFactory;
    }

    @Transactional
    public void updateRating(String email, Long quizId, Integer rating) {
        try {
            Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
            if (optionalUserEntity.isEmpty()) throw new UserNotFoundException();

            Long userId = optionalUserEntity.get().getId();
            Optional<QuizRatingEntity> optionalQuizRatingEntity = quizRatingRepository.findQuizRatingByUserId(userId, quizId);
            if (optionalQuizRatingEntity.isEmpty()) {
                Optional<QuizEntity> optionalQuizEntity = quizRepository.findById(quizId);
                if (optionalQuizEntity.isEmpty()) throw new QuizNotFoundException();

                QuizRatingEntity quizRatingEntity = new QuizRatingEntity(
                        0L,
                        rating,
                        optionalUserEntity.get(),
                        optionalQuizEntity.get()
                );
                quizRatingRepository.save(quizRatingEntity);
            } else {
                quizRatingRepository.updateRatingByUserIdAndQuizId(userId, quizId, rating);
            }
        } catch (UserNotFoundException | QuizNotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public GetUserQuizRatingDto readUserQuizRating(Long quizId, String email) {
        try {
            Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
            if (optionalUserEntity.isEmpty()) throw new UserNotFoundException();

            Long userId = optionalUserEntity.get().getId();
            Optional<QuizRatingEntity> optionalQuizRatingEntity = quizRatingRepository.findQuizRatingByUserId(userId, quizId);
            return optionalQuizRatingEntity
                    .map(m -> new GetUserQuizRatingDto(
                                    m.getId(),
                                    m.getRating(),
                                    getQuizFactory.mapToDto(m.getQuiz())
                            )
                    )
                    .orElse(null);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public GetQuizRatingDto readQuizRating(Long quizId) {
        try {
            Double rating = quizRatingRepository.findAverageRatingByQuizId(quizId);
            return new GetQuizRatingDto(rating);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnexpectedException();
        }
    }
}



























