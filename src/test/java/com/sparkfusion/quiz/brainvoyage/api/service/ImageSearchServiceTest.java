package com.sparkfusion.quiz.brainvoyage.api.service;

import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayImageDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayResponse;
import com.sparkfusion.quiz.brainvoyage.api.exception.BadConnectionException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnableExecuteRequestException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.repository.PixabayRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ImageSearchServiceTest {

    @Mock
    private PixabayRepository pixabayRepository;

    @InjectMocks
    private ImageSearchService imageSearchService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testSearchImagesEmptyResponse() {
        PixabayResponse response = new PixabayResponse(Collections.emptyList());
        when(pixabayRepository.searchImages("test", 1, 20)).thenReturn(response);

        List<PixabayImageDto> result = imageSearchService.searchImages("test", 1, 20);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchImagesSuccess() {
        PixabayImageDto image = new PixabayImageDto("url", 640, 480, 1000, 500, 1, "user", "userImageUrl");
        PixabayResponse response = new PixabayResponse(Collections.singletonList(image));
        when(pixabayRepository.searchImages("test", 1, 20)).thenReturn(response);

        List<PixabayImageDto> result = imageSearchService.searchImages("test", 1, 20);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("url", result.get(0).getWebformatURL());
    }

    @Test
    public void testSearchImagesClientError() {
        when(pixabayRepository.searchImages("test", 1, 20)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        List<PixabayImageDto> result = imageSearchService.searchImages("test", 1, 20);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchImagesForbiddenClientError() {
        when(pixabayRepository.searchImages("test", 1, 20)).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

        assertThrows(UnableExecuteRequestException.class, () -> imageSearchService.searchImages("test", 1, 20));
    }

    @Test
    public void testSearchImagesNotFoundClientError() {
        when(pixabayRepository.searchImages("test", 1, 20)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(UnableExecuteRequestException.class, () -> imageSearchService.searchImages("test", 1, 20));
    }

    @Test
    public void testSearchImagesServerError() {
        when(pixabayRepository.searchImages("test", 1, 20)).thenThrow(HttpServerErrorException.class);

        assertThrows(UnexpectedException.class, () -> imageSearchService.searchImages("test", 1, 20));
    }

    @Test
    public void testSearchImagesRestClientException() {
        when(pixabayRepository.searchImages("test", 1, 20)).thenThrow(RestClientException.class);

        assertThrows(BadConnectionException.class, () -> imageSearchService.searchImages("test", 1, 20));
    }

    @Test
    public void testSearchImagesUnknownException() {
        when(pixabayRepository.searchImages("test", 1, 20)).thenThrow(RuntimeException.class);

        assertThrows(UnexpectedException.class, () -> imageSearchService.searchImages("test", 1, 20));
    }

    @Test
    public void testSearchImagesWithPagination() {
        PixabayImageDto image = new PixabayImageDto("url", 640, 480, 1000, 500, 1, "user", "userImageUrl");
        PixabayResponse response = new PixabayResponse(Collections.singletonList(image));

        when(pixabayRepository.searchImages("test", 2, 50)).thenReturn(response);

        List<PixabayImageDto> result = imageSearchService.searchImages("test", 2, 50);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("url", result.get(0).getWebformatURL());
    }
}
