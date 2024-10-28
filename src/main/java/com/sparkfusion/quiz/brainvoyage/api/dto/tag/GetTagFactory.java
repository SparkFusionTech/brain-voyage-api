package com.sparkfusion.quiz.brainvoyage.api.dto.tag;

import com.sparkfusion.quiz.brainvoyage.api.entity.quiz.TagEntity;
import org.springframework.stereotype.Component;

@Component
public class GetTagFactory {

    public GetTagDto mapToDto(TagEntity tag) {
        return new GetTagDto(tag.getId(), tag.getName(), tag.getQuiz());
    }

    public TagEntity mapToEntity(GetTagDto getTagDto) {
        TagEntity tag = new TagEntity();
        tag.setName(getTagDto.getName());
        tag.setQuiz(getTagDto.getQuiz());
        return tag;
    }
}
