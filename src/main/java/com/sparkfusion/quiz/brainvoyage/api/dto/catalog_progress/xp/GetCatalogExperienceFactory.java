package com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.xp;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogExperience;
import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogLevel;
import org.springframework.stereotype.Component;

@Component
public class GetCatalogExperienceFactory {

    public GetCatalogExperienceDto mapToDto(CatalogExperience catalogExperience) {
        CatalogLevel catalogLevel = catalogExperience.getCatalogLevel();
        return new GetCatalogExperienceDto(
                catalogExperience.getId(),
                catalogExperience.getCurrentXp(),
                catalogLevel.getXpCount(),
                catalogLevel.getName(),
                catalogLevel.getLevel(),
                catalogLevel.getColor()
        );
    }
}
