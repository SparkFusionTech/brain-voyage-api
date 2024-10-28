package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.tag.AddTagDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.AddTagFactory;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.GetTagDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.GetTagFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.TagEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final QuizRepository quizRepository;

    private final AddTagFactory addTagFactory;
    private final GetTagFactory getTagFactory;

    public TagService(TagRepository tagRepository, QuizRepository quizRepository, AddTagFactory addTagFactory, GetTagFactory getTagFactory) {
        this.tagRepository = tagRepository;
        this.quizRepository = quizRepository;
        this.addTagFactory = addTagFactory;
        this.getTagFactory = getTagFactory;
    }

    @Transactional(readOnly = true)
    public List<GetTagDto> readTagsByQuizId(Long quizId) {
        try {
            List<TagEntity> tags = tagRepository.readTagByQuizId(quizId);
            return tags.stream()
                    .map(getTagFactory::mapToDto)
                    .toList();
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public GetTagDto addTag(AddTagDto addTagDto) {
        try {
            Optional<QuizEntity> existingQuiz = quizRepository.findById(addTagDto.getQuizId());
            if (existingQuiz.isEmpty()) {
                throw new QuizNotFoundException();
            }

            TagEntity tag = tagRepository.save(addTagFactory.mapToEntity(addTagDto, existingQuiz.get()));
            return getTagFactory.mapToDto(tag);
        } catch (QuizNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }
}



























