package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.quiz_catalog.GetQuizCatalogDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.quiz_catalog.GetQuizCatalogFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.catalog.QuizCatalogEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizCatalogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizCatalogService {

    private final QuizCatalogRepository repository;
    private final GetQuizCatalogFactory getQuizCatalogFactory;

    public QuizCatalogService(QuizCatalogRepository repository, GetQuizCatalogFactory getQuizCatalogFactory) {
        this.repository = repository;
        this.getQuizCatalogFactory = getQuizCatalogFactory;
    }

    @Transactional(readOnly = true)
    public List<GetQuizCatalogDto> readQuizCatalog() {
        try {
            List<QuizCatalogEntity> quizCatalogEntities = repository.findAll();
            return quizCatalogEntities.stream()
                    .map(getQuizCatalogFactory::mapToDto)
                    .toList();
        } catch (Exception e) {
            throw new UnexpectedException("Unknown exception while reading quiz catalog");
        }
    }
}
