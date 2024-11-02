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
import com.sparkfusion.quiz.brainvoyage.api.worker.image.ImageWorker;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizCatalogRepository quizCatalogRepository;

    private final GetQuizFactory getQuizFactory;
    private final AddQuizFactory addQuizFactory;

    private final ImageWorker imageWorker;

    public QuizService(
            QuizRepository quizRepository,
            UserRepository userRepository,
            QuizCatalogRepository quizCatalogRepository,
            GetQuizFactory getQuizFactory,
            AddQuizFactory addQuizFactory,
            ImageWorker imageWorker
    ) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.quizCatalogRepository = quizCatalogRepository;
        this.getQuizFactory = getQuizFactory;
        this.addQuizFactory = addQuizFactory;
        this.imageWorker = imageWorker;
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
    public List<GetQuizDto> readAllQuizzesByType(Long catalogId) {
        try {
            List<QuizEntity> quizzes = quizRepository.findAllByType(catalogId);
            return quizzes.stream()
                    .map(getQuizFactory::mapToDto)
                    .toList();
        } catch (Exception exception) {
            throw new UnexpectedException("Error retrieving quizzes by type");
        }
    }

    @Transactional(readOnly = true)
    public GetQuizDto readQuizById(Long quizId) {
        try {
            Optional<QuizEntity> quiz = quizRepository.findById(quizId);
            if (quiz.isEmpty()) throw new QuizNotFoundException();

            return getQuizFactory.mapToDto(quiz.get());
        } catch (QuizNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public GetQuizDto addQuiz(AddQuizDto addQuizDto, @NotNull MultipartFile image, String userEmail) {
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(userEmail);
            if (existingUser.isEmpty()) {
                throw new UserNotFoundException("User with email " + userEmail + " was not found!");
            }

            Optional<QuizCatalogEntity> existingCatalog = quizCatalogRepository.findById(
                    Long.parseLong(addQuizDto.getCatalogId().toString()));
            if (existingCatalog.isEmpty()) {
                throw new QuizCatalogNotFoundException();
            }

            String imageUrl = imageWorker.saveImage(image, ImageWorker.ImageType.QUIZ);

            QuizEntity quiz = addQuizFactory.mapToEntity(addQuizDto, existingUser.get(), existingCatalog.get(), imageUrl);
            QuizEntity savedQuiz = quizRepository.save(quiz);
            return getQuizFactory.mapToDto(savedQuiz);
        } catch (UserNotFoundException | QuizCatalogNotFoundException e) {
            throw e;
        } catch (Exception exception) {
            throw new UnexpectedException("Error adding quiz");
        }
    }
}




























