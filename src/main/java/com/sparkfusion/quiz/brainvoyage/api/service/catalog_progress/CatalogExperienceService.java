package com.sparkfusion.quiz.brainvoyage.api.service.catalog_progress;

import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.xp.GetCatalogExperienceDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.xp.GetCatalogExperienceFactory;
import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogExperience;
import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogLevel;
import com.sparkfusion.quiz.brainvoyage.api.entity.user.UserEntity;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.exception.catalog_xp.CatalogExperienceNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress.CatalogExperienceRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.catalog_progress.CatalogLevelRepository;
import com.sparkfusion.quiz.brainvoyage.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogExperienceService {

    private final CatalogExperienceRepository catalogExperienceRepository;
    private final CatalogLevelRepository catalogLevelRepository;
    private final UserRepository userRepository;

    private final GetCatalogExperienceFactory getCatalogExperienceFactory;

    public CatalogExperienceService(
            CatalogExperienceRepository catalogExperienceRepository,
            CatalogLevelRepository catalogLevelRepository,
            UserRepository userRepository,
            GetCatalogExperienceFactory getCatalogExperienceFactory
    ) {
        this.catalogExperienceRepository = catalogExperienceRepository;
        this.catalogLevelRepository = catalogLevelRepository;
        this.userRepository = userRepository;
        this.getCatalogExperienceFactory = getCatalogExperienceFactory;
    }

    @Transactional
    public GetCatalogExperienceDto readCurrentCatalogExperience(
            String userEmail
    ) throws UserNotFoundException, UnexpectedException {
        try {
            Optional<UserEntity> user = userRepository.findByEmail(userEmail);
            if (user.isEmpty()) throw new UserNotFoundException();

            Optional<CatalogExperience> catalogExperienceOptional = catalogExperienceRepository.findByUser(userEmail);
            CatalogExperience catalogExperience = catalogExperienceOptional.orElseGet(() -> {
                        initCatalogLevel(user.get());

                        Optional<CatalogLevel> catalogLevel = catalogLevelRepository.readCatalogLevel(0);
                        if (catalogLevel.isEmpty()) throw new UnexpectedException();

                        CatalogExperience ce = new CatalogExperience();
                        ce.setUser(user.get());
                        ce.setCurrentXp(0);
                        ce.setCatalogLevel(catalogLevel.get());

                        return catalogExperienceRepository.save(ce);
                    }
            );

            return getCatalogExperienceFactory.mapToDto(catalogExperience);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

    @Transactional
    public void initCatalogLevel(UserEntity user) throws UnexpectedException {
        try {
            Optional<CatalogLevel> catalogLevel = catalogLevelRepository.readCatalogLevel(0);
            if (catalogLevel.isEmpty()) return;

            CatalogExperience catalogExperience = new CatalogExperience();
            catalogExperience.setCurrentXp(0);
            catalogExperience.setCatalogLevel(catalogLevel.get());
            catalogExperience.setUser(user);

            catalogExperienceRepository.save(catalogExperience);
        } catch (Exception e) {
            throw new UnexpectedException(e.getMessage());
        }
    }

    @Transactional
    public void updateCatalogExperience(
            String userEmail, Integer addXp
    ) throws UserNotFoundException, CatalogExperienceNotFoundException, UnexpectedException {
        try {
            Optional<UserEntity> user = userRepository.findByEmail(userEmail);
            if (user.isEmpty()) throw new UserNotFoundException();

            Optional<CatalogExperience> catalogExperienceOptional = catalogExperienceRepository.findByUser(userEmail);
            if (catalogExperienceOptional.isEmpty()) throw new CatalogExperienceNotFoundException();

            CatalogExperience catalogExperience = catalogExperienceOptional.get();
            Optional<CatalogLevel> nextLevel = catalogLevelRepository.findById(catalogExperience.getCatalogLevel().getId() + 1);
            if (nextLevel.isEmpty()) throw new UnexpectedException();

            if (catalogExperience.getCurrentXp() + addXp >= catalogExperience.getCatalogLevel().getXpCount()) {
                catalogExperience.setCurrentXp(catalogExperience.getCurrentXp() + addXp - catalogExperience.getCatalogLevel().getXpCount());
                catalogExperience.setCatalogLevel(nextLevel.get());
            } else {
                catalogExperience.setCurrentXp(catalogExperience.getCurrentXp() + addXp);
            }

            catalogExperienceRepository.save(catalogExperience);
        } catch (UserNotFoundException | CatalogExperienceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException(e.getMessage());
        }
    }
}
















