package com.sparkfusion.quiz.brainvoyage.api.exception.catalog_xp;

public class CatalogExperienceNotFoundException extends RuntimeException {
    public CatalogExperienceNotFoundException() {
        super("Catalog experience was not found");
    }
}
