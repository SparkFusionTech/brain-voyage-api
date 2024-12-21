package com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.progress;

import com.sparkfusion.quiz.brainvoyage.api.entity.catalog_progress.CatalogProgress;
import org.springframework.stereotype.Component;

@Component
public class GetCatalogProgressFactory {

    public GetCatalogProgressDto mapToDto(CatalogProgress catalogProgress) {
        return new GetCatalogProgressDto(
                catalogProgress.getId(),
                catalogProgress.getPlayCount(),
                catalogProgress.getNextTryAt()
        );
    }
}
