package com.sparkfusion.quiz.brainvoyage.api.exception.catalog_xp;

public class CatalogProgressNotFoundException extends RuntimeException {
    public CatalogProgressNotFoundException() {
        super("Catalog progress was not found");
    }
}
