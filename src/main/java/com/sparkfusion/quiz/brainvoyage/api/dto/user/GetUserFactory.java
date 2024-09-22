package com.sparkfusion.quiz.brainvoyage.api.dto.user;

import com.sparkfusion.quiz.brainvoyage.api.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class GetUserFactory {

    public GetUserDto mapToDto(UserEntity user) {
        return new GetUserDto(user.getId(), user.getEmail(), user.getPassword(), user.getCreatedAt());
    }
}
