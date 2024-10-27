package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.*;
import com.sparkfusion.quiz.brainvoyage.api.encryptor.PasswordEncryptor;
import com.sparkfusion.quiz.brainvoyage.api.entity.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserAlreadyExistsException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.jwt.JwtResponse;
import com.sparkfusion.quiz.brainvoyage.api.jwt.JwtUtils;
import com.sparkfusion.quiz.brainvoyage.api.repository.UserRepository;
import com.sparkfusion.quiz.brainvoyage.api.worker.image.ImageWorker;
import com.sparkfusion.quiz.brainvoyage.api.worker.image.error.NotImageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final GetUserFactory getUserFactory;
    private final AddUserFactory addUserFactory;
    private final GetUserInfoFactory getUserInfoFactory;

    private final PasswordEncryptor passwordEncryptor;
    private final JwtUtils jwtUtils;

    private final ImageWorker imageWorker;

    public UserService(
            UserRepository userRepository,
            GetUserFactory getUserFactory,
            AddUserFactory addUserFactory,
            GetUserInfoFactory getUserInfoFactory,
            PasswordEncryptor passwordEncryptor,
            JwtUtils jwtUtils,
            ImageWorker imageWorker
    ) {
        this.userRepository = userRepository;
        this.getUserFactory = getUserFactory;
        this.addUserFactory = addUserFactory;
        this.getUserInfoFactory = getUserInfoFactory;
        this.passwordEncryptor = passwordEncryptor;
        this.jwtUtils = jwtUtils;
        this.imageWorker = imageWorker;
    }

    @Transactional(readOnly = true)
    public GetUserInfoDto readUserInfo(String email) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            if (userEntity.isEmpty()) {
                throw new UserNotFoundException();
            }

            return getUserInfoFactory.mapToDto(userEntity.get());
        } catch (Exception e) {
            throw new UnexpectedException("Unknown error during user info reading");
        }
    }

    @Transactional
    public AddUserDto registerUser(String email, String password, MultipartFile accountIcon) {
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                throw new UserAlreadyExistsException("User with email " + email + " already exists");
            }

            String iconUrl;
            if (accountIcon == null || accountIcon.getContentType() == null) {
                iconUrl = imageWorker.getEmptyAccountIconUrl();
            } else if (!accountIcon.getContentType().startsWith("image/")) {
                throw new NotImageException();
            } else {
                iconUrl = imageWorker.saveImage(accountIcon);
            }

            UserEntity userEntity = addUserFactory.mapToEntity(
                    new AddUserDto(email, password),
                    iconUrl,
                    UserEntity.generateUserName(),
                    passwordEncryptor
            );
            UserEntity savedUser = userRepository.save(userEntity);

            return addUserFactory.mapToDto(savedUser);
        } catch (UserAlreadyExistsException | NotImageException | UnexpectedException exception) {
            throw exception;
        } catch (Exception e) {
            throw new UnexpectedException("Error registering user with email " + email + "!");
        }
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
    public UserExistsDto isUserExists(String email) {
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(email);
            return new UserExistsDto(existingUser.isPresent());
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
}
