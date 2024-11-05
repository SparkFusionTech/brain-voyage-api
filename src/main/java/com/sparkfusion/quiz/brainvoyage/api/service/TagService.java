package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.tag.AddTagDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.AddTagFactory;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.GetTagDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.tag.GetTagFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.QuizEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.TagEntity;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.QuizNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.repository.QuizRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.TagRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    private final AddTagFactory addTagFactory;
    private final GetTagFactory getTagFactory;

    public TagService(
            TagRepository tagRepository,
            QuizRepository quizRepository,
            UserRepository userRepository,
            AddTagFactory addTagFactory,
            GetTagFactory getTagFactory
    ) {
        this.tagRepository = tagRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.addTagFactory = addTagFactory;
        this.getTagFactory = getTagFactory;
    }

    @Transactional(readOnly = true)
    public List<GetTagDto> readTagsByQuizId(Long quizId) {
        try {
            List<TagEntity> tags = tagRepository.readTagsByQuizId(quizId);
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

    @Transactional
    public Integer addTags(List<String> names, Long quizId) {
        try {
            Optional<QuizEntity> existingQuiz = quizRepository.findById(quizId);
            if (existingQuiz.isEmpty()) {
                throw new QuizNotFoundException();
            }

            Integer count = 0;
            for (String name : names) {
                AddTagDto addTagDto = new AddTagDto(name, quizId);
                tagRepository.save(addTagFactory.mapToEntity(addTagDto, existingQuiz.get()));
                count++;
            }
            return count;
        } catch (QuizNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public void updateTags(List<String> newTags, Long quizId, String email) {
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(email);
            if (existingUser.isEmpty()) {
                throw new UserNotFoundException();
            }

            Optional<QuizEntity> existingQuiz = quizRepository.findQuizByUserIdAndQuizId(existingUser.get().getId(), quizId);
            if (existingQuiz.isEmpty()) {
                throw new QuizNotFoundException();
            }

            QuizEntity quiz = existingQuiz.get();
            List<TagEntity> existingTags = tagRepository.readTagsByQuizId(quizId);

            Set<String> existingTagNames = existingTags.stream()
                    .map(TagEntity::getName)
                    .collect(Collectors.toSet());
            Set<String> newTagNames = new HashSet<>(newTags);

            existingTagNames.stream()
                    .filter(tag -> !newTagNames.contains(tag))
                    .forEach(tag -> tagRepository.deleteAllByNameAndQuizId(tag, quizId));

            newTagNames.stream()
                    .filter(tag -> !existingTagNames.contains(tag))
                    .forEach(tag -> {
                        AddTagDto addTagDto = new AddTagDto(tag, quizId);
                        tagRepository.save(addTagFactory.mapToEntity(addTagDto, quiz));
                    });
        } catch (QuizNotFoundException | UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }
}



























