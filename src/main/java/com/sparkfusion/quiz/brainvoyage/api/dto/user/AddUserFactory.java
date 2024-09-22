package com.sparkfusion.quiz.brainvoyage.api.dto.user;

import com.sparkfusion.quiz.brainvoyage.api.encryptor.PasswordEncryptor;
import com.sparkfusion.quiz.brainvoyage.api.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class AddUserFactory {

    public AddUserDto mapToDto(UserEntity user) {
        return new AddUserDto(user.getEmail(), user.getPassword());
    }

    public UserEntity mapToEntity(AddUserDto addUserDto, PasswordEncryptor passwordEncryptor) {
        UserEntity user = new UserEntity();
        user.setEmail(addUserDto.getEmail());
        user.setPassword(passwordEncryptor.encrypt(addUserDto.getPassword()));
        return user;
    }
}
