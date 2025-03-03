package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.answer.AddAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.question.*;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.AnswerEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuestionEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.storage.FailedStorageConnectionException;
import com.sparkfusion.quiz.brainvoyage.api.repository.AnswerRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuestionRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizRepository;
import com.sparkfusion.quiz.brainvoyage.api.worker.image.ImageWorker;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuizRepository quizRepository;

    private final AnswerService answerService;

    private final AddQuestionFactory addQuestionFactory;
    private final GetQuestionFactory getQuestionFactory;
    private final GetQuestionWithAnswerFactory getQuestionWithAnswerFactory;

    private final ImageWorker imageWorker;

    public QuestionService(
            QuestionRepository questionRepository,
            QuizRepository quizRepository,
            AnswerRepository answerRepository,
            AddQuestionFactory addQuestionFactory,
            GetQuestionFactory getQuestionFactory,
            GetQuestionWithAnswerFactory getQuestionWithAnswerFactory,
            ImageWorker imageWorker,
            AnswerService answerService
    ) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.addQuestionFactory = addQuestionFactory;
        this.getQuestionFactory = getQuestionFactory;
        this.imageWorker = imageWorker;
        this.answerService = answerService;
        this.answerRepository = answerRepository;
        this.getQuestionWithAnswerFactory = getQuestionWithAnswerFactory;
    }

    @Transactional(readOnly = true)
    public List<GetQuestionWithAnswerDto> readQuestionsWithAnswersByQuizId(Long quizId) {
        try {
            List<QuestionEntity> questions = questionRepository.readQuestionByQuizId(quizId);
            List<GetQuestionWithAnswerDto> questionsWithAnswers = new ArrayList<>(questions.size());

            for (QuestionEntity question : questions) {
                List<AnswerEntity> answers = answerRepository.readAnswersByQuestionId(question.getId());
                questionsWithAnswers.add(getQuestionWithAnswerFactory.mapToDto(question, answers));
            }

            return questionsWithAnswers;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public List<GetQuestionWithAnswerDto> readQuestionsWithAnswersForOnlineGameByCategoryId(
            @Nullable Long catalogId
    ) {
        try {
            List<QuestionEntity> questions;
            if (catalogId == null) {
                questions = questionRepository.findRandomQuestions();
                System.out.println(questions.toString());
            } else {
                questions = questionRepository.findRandomQuestions(catalogId);
                System.out.println(questions.toString());
            }

            System.out.println("READ FINISHED");
            List<GetQuestionWithAnswerDto> questionsWithAnswers = new ArrayList<>(questions.size());
            for (QuestionEntity question : questions) {
                System.out.println(question.toString());
                List<AnswerEntity> answers = answerRepository.readAnswersByQuestionId(question.getId());
                questionsWithAnswers.add(getQuestionWithAnswerFactory.mapToDto(question, answers));
            }

            return questionsWithAnswers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public List<GetQuestionDto> readQuestionsByQuizId(Long quizId) {
        try {
            List<QuestionEntity> questions = questionRepository.readQuestionByQuizId(quizId);
            return questions.stream()
                    .map(getQuestionFactory::mapToDto)
                    .toList();
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public GetQuestionDto addQuestion(AddQuestionDto addQuestionDto, MultipartFile image) {
        try {
            Optional<QuizEntity> quiz = quizRepository.findById(addQuestionDto.getQuizId());
            if (quiz.isEmpty()) {
                throw new QuizNotFoundException();
            }

            String imageUrl = imageWorker.saveImage(image, ImageWorker.ImageType.QUESTION);

            QuestionEntity question = questionRepository.save(addQuestionFactory.mapToEntity(addQuestionDto, quiz.get(), imageUrl));
            for (AddAnswerDto addAnswerDto : addQuestionDto.getAnswers()) {
                answerService.addAnswer(addAnswerDto, question.getId());
            }
            return getQuestionFactory.mapToDto(question);
        } catch (QuizNotFoundException | FailedStorageConnectionException | UnexpectedException e) {
            throw e;
        } catch (Exception exception) {
            throw new UnexpectedException();
        }
    }
}






















