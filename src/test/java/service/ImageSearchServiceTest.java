package service;

import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayImageDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayResponse;
import com.sparkfusion.quiz.brainvoyage.api.exception.BadConnectionException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnableExecuteRequestException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.repository.PixabayRepository;
import com.sparkfusion.quiz.brainvoyage.api.service.ImageSearchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        when(pixabayRepository.searchImages("test")).thenReturn(response);

        List<PixabayImageDto> result = imageSearchService.searchImages("test");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchImagesSuccess() {
        PixabayImageDto image = new PixabayImageDto("url", 640, 480, 1000, 500, 1, "user", "userImageUrl");
        PixabayResponse response = new PixabayResponse(Collections.singletonList(image));
        when(pixabayRepository.searchImages("test")).thenReturn(response);

        List<PixabayImageDto> result = imageSearchService.searchImages("test");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("url", result.get(0).getWebformatURL());
    }

    @Test
    public void testSearchImagesClientError() {
        when(pixabayRepository.searchImages("test")).thenThrow(HttpClientErrorException.class);

        assertThrows(UnableExecuteRequestException.class, () -> imageSearchService.searchImages("test"));
    }

    @Test
    public void testSearchImagesServerError() {
        when(pixabayRepository.searchImages("test")).thenThrow(HttpServerErrorException.class);

        assertThrows(UnexpectedException.class, () -> imageSearchService.searchImages("test"));
    }

    @Test
    public void testSearchImagesRestClientException() {
        when(pixabayRepository.searchImages("test")).thenThrow(RestClientException.class);

        assertThrows(BadConnectionException.class, () -> imageSearchService.searchImages("test"));
    }

    @Test
    public void testSearchImagesUnknownException() {
        when(pixabayRepository.searchImages("test")).thenThrow(RuntimeException.class);

        assertThrows(UnexpectedException.class, () -> imageSearchService.searchImages("test"));
    }
}
