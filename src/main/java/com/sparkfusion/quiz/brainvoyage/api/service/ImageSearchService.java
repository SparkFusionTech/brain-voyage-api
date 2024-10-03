package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayImageDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayResponse;
import com.sparkfusion.quiz.brainvoyage.api.exception.BadConnectionException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnableExecuteRequestException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.repository.PixabayRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;

@Service
public class ImageSearchService {

    private final PixabayRepository repository;

    public ImageSearchService(PixabayRepository repository) {
        this.repository = repository;
    }

    public List<PixabayImageDto> searchImages(String query) {
        try {
            PixabayResponse response = repository.searchImages(query);
            if (response != null && response.getHits() != null) {
                return response.getHits();
            }
        } catch (HttpClientErrorException exception) {
            throw new UnableExecuteRequestException();
        } catch (HttpServerErrorException exception) {
            throw new UnexpectedException();
        } catch (RestClientException exception) {
            throw new BadConnectionException();
        } catch (Exception exception) {
            throw new UnexpectedException("Unknown exception during image searching - " + exception.getMessage());
        }

        return Collections.emptyList();
    }
}
