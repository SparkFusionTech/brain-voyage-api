package com.sparkfusion.quiz.brainvoyage.api.config;

import com.sparkfusion.quiz.brainvoyage.api.worker.image.ImageUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StorageConfig implements WebMvcConfigurer {

    private final String resourceLocation;

    public StorageConfig(@Value("${RESOURCE_LOCATION}") String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourceLocation + ImageUtils.UPLOAD_DIRECTORY + "/");
    }
}

















