package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.news.NewsDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.News;
import com.codecool.budapestgo.dao.repository.NewsRepository;
import com.codecool.budapestgo.utils.DtoMapper;
import com.codecool.budapestgo.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<NewsDTO> getAllNews(){
        return newsRepository.findAll().stream().map(NewsDTO::of).toList();
    }
    public NewsDTO getNewsByTitle(String title){
        return NewsDTO.of(newsRepository.findByTitle(title).orElseThrow(() -> new NotFoundException("News")));
    }
    public ResponseEntity<String> addNews(NewsDTO newsDTO){
        validateNewsExistence(newsDTO.title(),true);
        News newNews = DtoMapper.toEntity(newsDTO);
        newsRepository.save(newNews);

        return Response.successful("Created");
    }
    public ResponseEntity<String> deleteNews(String title){
        validateNewsExistence(title, false);
        newsRepository.deleteByTitle(title);

        return Response.successful("Deleted");
    }
    public ResponseEntity<String> updateNews(NewsDTO newsDTO){
        validateNewsExistence(newsDTO.title(), false);
        News newsToUpdate = DtoMapper.toEntity(newsDTO);
        newsRepository.saveAndFlush(newsToUpdate);

        return Response.successful("Updated");
    }
    private void validateNewsExistence(String title, boolean validateTo){
        if(newsRepository.findByTitle(title).isPresent()) {
            if (validateTo) {
                throw new DataIntegrityViolationException("Article with title " + title + " already exist");
            }
        }
        else if(newsRepository.findByTitle(title).isEmpty() && !validateTo){
                throw new NotFoundException("News");
        }
    }
}
