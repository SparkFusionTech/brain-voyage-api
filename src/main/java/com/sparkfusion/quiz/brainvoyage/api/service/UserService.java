package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.AddUserDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.user.AddUserFactory;
import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserFactory;
import com.sparkfusion.quiz.brainvoyage.api.encryptor.PasswordEncryptor;
import com.sparkfusion.quiz.brainvoyage.api.entity.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserAlreadyExistsException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.jwt.JwtResponse;
import com.sparkfusion.quiz.brainvoyage.api.jwt.JwtUtils;
import com.sparkfusion.quiz.brainvoyage.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final GetUserFactory getUserFactory;
    private final AddUserFactory addUserFactory;

    private final PasswordEncryptor passwordEncryptor;
    private final JwtUtils jwtUtils;

    public UserService(
            UserRepository userRepository,
            GetUserFactory getUserFactory,
            AddUserFactory addUserFactory,
            PasswordEncryptor passwordEncryptor,
            JwtUtils jwtUtils
    ) {
        this.userRepository = userRepository;
        this.getUserFactory = getUserFactory;
        this.addUserFactory = addUserFactory;
        this.passwordEncryptor = passwordEncryptor;
        this.jwtUtils = jwtUtils;
    }

    @Transactional(readOnly = true)
    public UserEntity loadUserByEmail(String email) {
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) return existingUser.get();
            else throw new UserNotFoundException("User was not found with email - " + email);
        } catch (UserNotFoundException exception) {
            throw exception;
        } catch (Exception e) {
            throw new UnexpectedException("Error loading user existing with email " + email + "!");
        }
    }

    @Transactional(readOnly = true)
    public JwtResponse loginAndGenerateToken(AddUserDto addUserDto) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(addUserDto.getEmail());

        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            if (passwordEncryptor.matches(addUserDto.getPassword(), user.getPassword())) {
                String token = jwtUtils.generateToken(user.getEmail());
                return new JwtResponse(token);
            } else {
                throw new UnexpectedException("Invalid credentials");
            }
        } else {
            throw new UnexpectedException("User not found");
        }
    }

    @Transactional(readOnly = true)
    public Boolean isEmailFree(String email) {
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(email);
            return existingUser.isPresent();
        } catch (Exception exception) {
            throw new UnexpectedException("Error checking user existing with email " + email + "!");
        }
    }

    @Transactional(readOnly = true)
    public List<GetUserDto> readAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(getUserFactory::mapToDto)
                .toList();
    }

    @Transactional
    public AddUserDto addUser(AddUserDto addUserDto) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(addUserDto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + addUserDto.getEmail() + " already exists");
        }

        UserEntity userEntity = addUserFactory.mapToEntity(addUserDto, passwordEncryptor);
        UserEntity savedUser = userRepository.save(userEntity);
        return addUserFactory.mapToDto(savedUser);
    }
}
