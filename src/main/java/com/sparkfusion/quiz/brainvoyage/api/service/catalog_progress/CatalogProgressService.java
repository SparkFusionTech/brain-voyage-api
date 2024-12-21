package com.sparkfusion.quiz.brainvoyage.api.service.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.progress.GetCatalogProgressDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.progress.GetCatalogProgressFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogProgress;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.catalog_xp.CatalogProgressNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress.CatalogProgressRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CatalogProgressService {

    private final CatalogProgressRepository catalogProgressRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    private final GetCatalogProgressFactory getCatalogProgressFactory;

    public CatalogProgressService(CatalogProgressRepository catalogProgressRepository, UserRepository userRepository, QuizRepository quizRepository, GetCatalogProgressFactory getCatalogProgressFactory) {
        this.catalogProgressRepository = catalogProgressRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.getCatalogProgressFactory = getCatalogProgressFactory;
    }

    @Transactional
    public void updateNextTry(
            String userEmail,
            Long quizId
    ) throws UserNotFoundException, QuizNotFoundException, CatalogProgressNotFoundException, UnexpectedException {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
            if (optionalUser.isEmpty()) throw new UserNotFoundException();

            Optional<QuizEntity> optionalQuiz = quizRepository.findById(quizId);
            if (optionalQuiz.isEmpty()) throw new QuizNotFoundException();

            Optional<CatalogProgress> optionalCatalogProgress = catalogProgressRepository.findByQuizAndUser(userEmail, quizId);
            if (optionalCatalogProgress.isEmpty()) throw new CatalogProgressNotFoundException();

            CatalogProgress updatedCatalogProgress = optionalCatalogProgress.get();
            updatedCatalogProgress.setNextTryAt(LocalDateTime.now());
            catalogProgressRepository.save(updatedCatalogProgress);
        } catch (UserNotFoundException | QuizNotFoundException | CatalogProgressNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

    @Transactional
    public GetCatalogProgressDto readCatalogProgressByUserEmailAndQuizId(
            String userEmail,
            Long quizId
    ) throws UserNotFoundException, QuizNotFoundException, UnexpectedException {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
            if (optionalUser.isEmpty()) throw new UserNotFoundException();

            Optional<QuizEntity> optionalQuiz = quizRepository.findById(quizId);
            if (optionalQuiz.isEmpty()) throw new QuizNotFoundException();

            Optional<CatalogProgress> optionalCatalogProgress = catalogProgressRepository.findByQuizAndUser(userEmail, quizId);
            CatalogProgress currentCatalogProgress;
            if (optionalCatalogProgress.isEmpty()) {
                CatalogProgress catalogProgress = new CatalogProgress();
                LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(1L);

                catalogProgress.setNextTryAt(localDateTime);
                catalogProgress.setPlayCount(1);
                catalogProgress.setUser(optionalUser.get());
                catalogProgress.setQuiz(optionalQuiz.get());

                currentCatalogProgress = catalogProgressRepository.save(catalogProgress);
            } else {
                CatalogProgress tempCatalogProgress = optionalCatalogProgress.get();
                tempCatalogProgress.setPlayCount(tempCatalogProgress.getPlayCount() + 1);
                currentCatalogProgress = catalogProgressRepository.save(tempCatalogProgress);
            }

            return getCatalogProgressFactory.mapToDto(currentCatalogProgress);
        } catch (UserNotFoundException | QuizNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException(e.getMessage());
        }
    }
}

























