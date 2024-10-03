package com.sparkfusion.quiz.brainvoyage.api.repository;

import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PixabayRepository {

    private final String pixabayUrl;
    private final String pixabayKey;

    private final RestTemplate restTemplate;

    public PixabayRepository(RestTemplate restTemplate) {
        this.pixabayUrl = System.getenv("PIXABAY_URL");
        this.pixabayKey = System.getenv("PIXABAY_KEY");

        if (this.pixabayUrl == null || this.pixabayUrl.isEmpty()) {
            throw new IllegalArgumentException("PIXABAY_URL environment variable is not set!");
        }
        if (this.pixabayKey == null || this.pixabayKey.isEmpty()) {
            throw new IllegalArgumentException("PIXABAY_KEY environment variable is not set!");
        }

        this.restTemplate = restTemplate;
    }

    public PixabayResponse searchImages(String query) {
        String url = pixabayUrl + "?key=" + pixabayKey + "&q=" + query + "&image_type=photo";
        return restTemplate.getForObject(url, PixabayResponse.class);
    }
}
