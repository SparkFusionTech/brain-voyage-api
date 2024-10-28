package com.sparkfusion.quiz.brainvoyage.api.dto.user;

import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class GetUserInfoFactory  {

    public GetUserInfoDto mapToDto(UserEntity userEntity) {
        return new GetUserInfoDto(userEntity.getIconUrl(), userEntity.getName());
    }
}
