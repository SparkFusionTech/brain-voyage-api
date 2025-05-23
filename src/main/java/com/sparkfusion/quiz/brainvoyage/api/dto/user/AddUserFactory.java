package com.sparkfusion.quiz.brainvoyage.api.dto.user;

import com.sparkfusion.quiz.brainvoyage.api.encryptor.PasswordEncryptor;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class AddUserFactory {

    public AddUserDto mapToDto(UserEntity user) {
        return new AddUserDto(user.getEmail(), user.getPassword());
    }

    public UserEntity mapToEntity(
            AddUserDto addUserDto,
            String iconUrl,
            String name,
            PasswordEncryptor passwordEncryptor
    ) {
        UserEntity user = new UserEntity();
        user.setEmail(addUserDto.getEmail());
        user.setPassword(passwordEncryptor.encrypt(addUserDto.getPassword()));
        user.setIconUrl(iconUrl);
        user.setName(name);
        return user;
    }
}
