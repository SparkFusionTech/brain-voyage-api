package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.AddQuizDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.AddQuizFactory;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.GetQuizDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz.GetQuizFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.catalog.QuizCatalogEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizCatalogNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizCatalogRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizCatalogRepository quizCatalogRepository;

    private final GetQuizFactory getQuizFactory;
    private final AddQuizFactory addQuizFactory;

    public QuizService(
            QuizRepository quizRepository,
            UserRepository userRepository,
            QuizCatalogRepository quizCatalogRepository,
            GetQuizFactory getQuizFactory,
            AddQuizFactory addQuizFactory
    ) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.quizCatalogRepository = quizCatalogRepository;
        this.getQuizFactory = getQuizFactory;
        this.addQuizFactory = addQuizFactory;
    }

    @Transactional
    public Boolean deleteQuiz(Long id) {
        try {
            Optional<QuizEntity> quiz = quizRepository.findById(id);
            if (quiz.isEmpty()) {
                throw new QuizNotFoundException("Quiz was not found with id - " + id);
            }

            return quizRepository.deleteQuizById(id) > 0;
        } catch (QuizNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new UnexpectedException("Error quiz deletion");
        }
    }

    @Transactional(readOnly = true)
    public List<GetQuizDto> readAllQuizzesByType(Integer type) {
        try {
            List<QuizEntity> quizzes = quizRepository.findAllByType(type);
            return quizzes.stream()
                    .map(getQuizFactory::mapToDto)
                    .toList();
        } catch (Exception exception) {
            throw new UnexpectedException("Error retrieving quizzes by type");
        }
    }

    @Transactional
    public GetQuizDto addQuiz(AddQuizDto addQuizDto) {
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(addQuizDto.getUserEmail());
            if (existingUser.isEmpty()) {
                throw new UserNotFoundException("User with email " + addQuizDto.getUserEmail() + " was not found!");
            }

            Optional<QuizCatalogEntity> existingCatalog = quizCatalogRepository.findById(
                    Long.parseLong(addQuizDto.getCatalogType().toString()));
            if (existingCatalog.isEmpty()) {
                throw new QuizCatalogNotFoundException();
            }

            QuizEntity quiz = addQuizFactory.mapToEntity(addQuizDto, existingUser.get(), existingCatalog.get());
            QuizEntity savedQuiz = quizRepository.save(quiz);
            return getQuizFactory.mapToDto(savedQuiz);
        } catch (UserNotFoundException | QuizCatalogNotFoundException e) {
            throw e;
        } catch (Exception exception) {
            throw new UnexpectedException("Error adding quiz");
        }
    }
}




























