package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.answer.AddAnswerDto;
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
import com.sparkfusion.quiz.brainvoyage.api.worker.image.ImageWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    private final AnswerService answerService;

    private final AddQuestionFactory addQuestionFactory;
    private final GetQuestionFactory getQuestionFactory;

    private final ImageWorker imageWorker;

    public QuestionService(
            QuestionRepository questionRepository,
            QuizRepository quizRepository,
            AddQuestionFactory addQuestionFactory,
            GetQuestionFactory getQuestionFactory,
            ImageWorker imageWorker,
            AnswerService answerService
    ) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.addQuestionFactory = addQuestionFactory;
        this.getQuestionFactory = getQuestionFactory;
        this.imageWorker = imageWorker;
        this.answerService = answerService;
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
        } catch (QuizNotFoundException e) {
            throw e;
        } catch (Exception exception) {
            throw new UnexpectedException();
        }
    }
}






















