package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.news.NewsDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.News;
import com.codecool.budapestgo.dao.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NewsServiceTest {
    @Mock
    private NewsRepository newsRepository;

    private NewsService newsService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        newsService = new NewsService(newsRepository);
    }

    @Test
    void testGetAllNews() {
        List<News> newsList = new ArrayList<>();
        newsList.add(buildNews("Title 1"));
        newsList.add(buildNews("Title 2"));
        when(newsRepository.findAll()).thenReturn(newsList);

        List<NewsDTO> result = newsService.getAllNews();

        assertEquals(newsList.size(), result.size());
        for (int i = 0; i < newsList.size(); i++) {
            assertNewsEquals(newsList.get(i), result.get(i));
        }
    }

    @Test
    void testGetNewsByTitle() {
        String title = "Title";
        News news = buildNews(title);
        when(newsRepository.findByTitle(title)).thenReturn(Optional.of(news));

        NewsDTO result = newsService.getNewsByTitle(title);

        assertNewsEquals(news, result);
    }

    @Test
    void testGetNewsByTitleThrowsNotFoundException() {
        String title = "Non-existent Title";
        when(newsRepository.findByTitle(title)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> newsService.getNewsByTitle(title));
    }

    @Test
    void testAddNews() {
        NewsDTO newsDTO = buildNewsDTO("New Title");
        when(newsRepository.findByTitle(newsDTO.title())).thenReturn(Optional.empty());

        ResponseEntity<String> response = newsService.addNews(newsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Created successfully", response.getBody());
        verify(newsRepository).save(any(News.class));
    }

    @Test
    void testAddNewsThrowsDataIntegrityViolationException() {
        NewsDTO newsDTO = buildNewsDTO("Duplicate Title");
        when(newsRepository.findByTitle(newsDTO.title())).thenReturn(Optional.of(buildNews(newsDTO.title())));

        assertThrows(DataIntegrityViolationException.class, () -> newsService.addNews(newsDTO));
    }

    private News buildNews(String title) {
        return News.builder()
                .title(title)
                .description("desc")
                .articleText("article text")
                .build();
    }

    private NewsDTO buildNewsDTO(String title) {
        return NewsDTO.builder()
                .title(title)
                .articleText("text")
                .description("desc")
                .imgData(new byte[0])
                .build();
    }

    private void assertNewsEquals(News expected, NewsDTO actual) {
        assertEquals(expected.getTitle(), actual.title());
        assertEquals(expected.getDescription(), actual.description());
        assertEquals(expected.getArticleText(), actual.articleText());
    }
}
