package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.answer.AddAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.answer.AddAnswerFactory;
import com.sparkfusion.quiz.brainvoyage.api.dto.answer.GetAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.answer.GetAnswerFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.AnswerEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuestionNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.repository.AnswerRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    private final AddAnswerFactory addAnswerFactory;
    private final GetAnswerFactory getAnswerFactory;

    public AnswerService(
            AnswerRepository answerRepository,
            QuestionRepository questionRepository,
            AddAnswerFactory addAnswerFactory,
            GetAnswerFactory getAnswerFactory
    ) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.addAnswerFactory = addAnswerFactory;
        this.getAnswerFactory = getAnswerFactory;
    }

    @Transactional(readOnly = true)
    public List<GetAnswerDto> readAnswerByQuestionId(Long questionId) {
        try {
            List<AnswerEntity> answers = answerRepository.readAnswersByQuestionId(questionId);
            return answers.stream()
                    .map(getAnswerFactory::mapToDto)
                    .toList();
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public GetAnswerDto addAnswer(AddAnswerDto addAnswerDto) {
        try {
            Optional<QuestionEntity> question = questionRepository.findById(addAnswerDto.getQuestionId());
            if (question.isEmpty()) {
                throw new QuestionNotFoundException();
            }

            AnswerEntity answer = answerRepository.save(addAnswerFactory.mapToEntity(addAnswerDto, question.get()));
            return getAnswerFactory.mapToDto(answer);
        } catch (QuestionNotFoundException e) {
            throw e;
        } catch (Exception exception) {
            throw new UnexpectedException();
        }
    }
}


















