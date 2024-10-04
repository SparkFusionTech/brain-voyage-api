package com.sparkfusion.quiz.brainvoyage.api.controller;

import com.sparkfusion.quiz.brainvoyage.api.dto.image_search.PixabayImageDto;
import com.sparkfusion.quiz.brainvoyage.api.exception.BadConnectionException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnableExecuteRequestException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception_handler.GlobalExceptionHandler;
import com.sparkfusion.quiz.brainvoyage.api.service.ImageSearchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageSearchControllerTest {

    @Mock
    private ImageSearchService imageSearchService;

    @InjectMocks
    private ImageSearchController imageSearchController;

    private MockMvc mockMvc;
    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(imageSearchController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    public void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testSearchImagesMissingAuthorizationHeader() throws Exception {
        mockMvc.perform(get("/images/search")
                        .param("query", "test")
                        .param("page", "1")
                        .param("per_page", "20"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchImagesMissingQueryParam() throws Exception {
        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("page", "1")
                        .param("per_page", "20"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchImagesSuccess() throws Exception {
        List<PixabayImageDto> mockResponse = List.of(
                new PixabayImageDto(
                        "https://example.com/image1.jpg",
                        640,
                        480,
                        1000,
                        150,
                        12345,
                        "user1",
                        "https://example.com/user1.jpg"
                ),
                new PixabayImageDto(
                        "https://example.com/image2.jpg",
                        800,
                        600,
                        500,
                        75,
                        67890,
                        "user2",
                        "https://example.com/user2.jpg"
                )
        );

        when(imageSearchService.searchImages("test", 1, 20)).thenReturn(mockResponse);

        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("query", "test")
                        .param("page", "1")
                        .param("per_page", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].webformatURL").value("https://example.com/image1.jpg"))
                .andExpect(jsonPath("$[0].webformatWidth").value(640))
                .andExpect(jsonPath("$[0].webformatHeight").value(480))
                .andExpect(jsonPath("$[0].views").value(1000))
                .andExpect(jsonPath("$[0].likes").value(150))
                .andExpect(jsonPath("$[0].user_id").value(12345))
                .andExpect(jsonPath("$[0].user").value("user1"))
                .andExpect(jsonPath("$[0].userImageURL").value("https://example.com/user1.jpg"))
                .andExpect(jsonPath("$[1].webformatURL").value("https://example.com/image2.jpg"))
                .andExpect(jsonPath("$[1].webformatWidth").value(800))
                .andExpect(jsonPath("$[1].webformatHeight").value(600))
                .andExpect(jsonPath("$[1].views").value(500))
                .andExpect(jsonPath("$[1].likes").value(75))
                .andExpect(jsonPath("$[1].user_id").value(67890))
                .andExpect(jsonPath("$[1].user").value("user2"))
                .andExpect(jsonPath("$[1].userImageURL").value("https://example.com/user2.jpg"));

        verify(imageSearchService, times(1)).searchImages("test", 1, 20);
    }

    @Test
    public void testSearchImagesNoResults() throws Exception {
        when(imageSearchService.searchImages("test", 1, 20)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("query", "test")
                        .param("page", "1")
                        .param("per_page", "20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testSearchImagesBadRequest() throws Exception {
        mockMvc.perform(get("/images/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchImagesWithInvalidPageAndPerPage() throws Exception {
        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("query", "test")
                        .param("page", "0")
                        .param("per_page", "201"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("query", "test")
                        .param("page", "-1")
                        .param("per_page", "3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchImagesWithPagination() throws Exception {
        List<PixabayImageDto> mockResponse = List.of(
                new PixabayImageDto(
                        "https://example.com/image1.jpg",
                        640,
                        480,
                        1000,
                        150,
                        12345,
                        "user1",
                        "https://example.com/user1.jpg"
                )
        );

        when(imageSearchService.searchImages("test", 2, 10)).thenReturn(mockResponse);

        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("query", "test")
                        .param("page", "2")
                        .param("per_page", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].webformatURL").value("https://example.com/image1.jpg"))
                .andExpect(jsonPath("$[0].webformatWidth").value(640))
                .andExpect(jsonPath("$[0].webformatHeight").value(480))
                .andExpect(jsonPath("$[0].views").value(1000))
                .andExpect(jsonPath("$[0].likes").value(150))
                .andExpect(jsonPath("$[0].user_id").value(12345))
                .andExpect(jsonPath("$[0].user").value("user1"))
                .andExpect(jsonPath("$[0].userImageURL").value("https://example.com/user1.jpg"));

        verify(imageSearchService, times(1)).searchImages("test", 2, 10);
    }

    @Test
    public void testSearchImagesThrowsUnableExecuteRequestException() throws Exception {
        when(imageSearchService.searchImages("test", 1, 20))
                .thenThrow(new UnableExecuteRequestException());

        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("query", "test")
                        .param("page", "1")
                        .param("per_page", "20"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchImagesThrowsBadConnectionException() throws Exception {
        when(imageSearchService.searchImages("test", 1, 20))
                .thenThrow(new BadConnectionException());

        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("query", "test")
                        .param("page", "1")
                        .param("per_page", "20"))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void testSearchImagesThrowsUnexpectedException() throws Exception {
        when(imageSearchService.searchImages("test", 1, 20))
                .thenThrow(new UnexpectedException("Unexpected error"));

        mockMvc.perform(get("/images/search")
                        .header("Authorization", "Bearer dummy_token")
                        .param("query", "test")
                        .param("page", "1")
                        .param("per_page", "20"))
                .andExpect(status().isInternalServerError());
    }
}
