package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.user.*;
import com.sparkfusion.quiz.brainvoyage.api.encryptor.PasswordEncryptor;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.InequalityPasswordsException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserAlreadyExistsException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.storage.FailedStorageConnectionException;
import com.sparkfusion.quiz.brainvoyage.api.jwt.JwtResponse;
import com.sparkfusion.quiz.brainvoyage.api.jwt.JwtUtils;
import com.sparkfusion.quiz.brainvoyage.api.repository.UserRepository;
import com.sparkfusion.quiz.brainvoyage.api.service.catalog_progress.CatalogExperienceService;
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
    private final CatalogExperienceService catalogExperienceService;

    private final GetUserFactory getUserFactory;
    private final AddUserFactory addUserFactory;
    private final GetUserInfoFactory getUserInfoFactory;

    private final PasswordEncryptor passwordEncryptor;
    private final JwtUtils jwtUtils;

    private final ImageWorker imageWorker;

    public UserService(
            UserRepository userRepository,
            CatalogExperienceService catalogExperienceService,
            GetUserFactory getUserFactory,
            AddUserFactory addUserFactory,
            GetUserInfoFactory getUserInfoFactory,
            PasswordEncryptor passwordEncryptor,
            JwtUtils jwtUtils,
            ImageWorker imageWorker
    ) {
        this.userRepository = userRepository;
        this.catalogExperienceService = catalogExperienceService;
        this.getUserFactory = getUserFactory;
        this.addUserFactory = addUserFactory;
        this.getUserInfoFactory = getUserInfoFactory;
        this.passwordEncryptor = passwordEncryptor;
        this.jwtUtils = jwtUtils;
        this.imageWorker = imageWorker;
    }

    @Transactional
    public void deleteAccount(String email, String password) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) throw new UserNotFoundException();

            if (!passwordEncryptor.matches(password, optionalUser.get().getPassword())) {
                throw new InequalityPasswordsException();
            }

            userRepository.delete(optionalUser.get());
        } catch (UserNotFoundException | InequalityPasswordsException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public GetUserDto readUser(String email) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) throw new UserNotFoundException();

            UserEntity user = optionalUser.get();
            return getUserFactory.mapToDto(user);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional
    public GetUserDto updatePassword(String email, String newPassword) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) throw new UserNotFoundException();

            UserEntity user = optionalUser.get();
            user.setPassword(passwordEncryptor.encrypt(newPassword));

            UserEntity newUser = userRepository.save(user);
            return getUserFactory.mapToDto(newUser);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
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
                iconUrl = imageWorker.saveImage(accountIcon, ImageWorker.ImageType.ACCOUNT);
            }

            UserEntity userEntity = addUserFactory.mapToEntity(
                    new AddUserDto(email, password),
                    iconUrl,
                    UserEntity.generateUserName(),
                    passwordEncryptor
            );
            UserEntity savedUser = userRepository.save(userEntity);

            try {
                catalogExperienceService.initCatalogLevel(savedUser);
            } catch (Exception ignore) {}

            return addUserFactory.mapToDto(savedUser);
        } catch (UserAlreadyExistsException | NotImageException | FailedStorageConnectionException |
                 UnexpectedException exception) {
            throw exception;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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


















