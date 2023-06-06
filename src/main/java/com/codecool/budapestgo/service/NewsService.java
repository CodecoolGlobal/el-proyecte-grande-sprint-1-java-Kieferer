package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.news.NewsDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.News;
import com.codecool.budapestgo.dao.repository.NewsRepository;
import com.codecool.budapestgo.utils.DtoMapper;
import com.codecool.budapestgo.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    public List<NewsDTO> getAllNews(){
        return newsRepository.findAll().stream().map(NewsDTO::of).toList();
    }
    public NewsDTO getNewsByTitle(String title){
        return NewsDTO.of(newsRepository.findByTitle(title).orElseThrow(() -> new NotFoundException("News")));
    }
    public ResponseEntity<String> addNews(NewsDTO newsDTO){
        validateNewsExistence(newsDTO.id(),true);
        News newNews = DtoMapper.toEntity(newsDTO);
        newsRepository.save(newNews);

        return Response.successful("Created");
    }
    public ResponseEntity<String> deleteNews(long id){
        validateNewsExistence(id, false);
        newsRepository.deleteById(id);

        return Response.successful("Deleted");
    }
    public ResponseEntity<String> updateNews(NewsDTO newsDTO){
        validateNewsExistence(newsDTO.id(), false);
        News updated = News.builder()
                .id(newsDTO.id())
                .title(newsDTO.title())
                .description(newsDTO.description())
                .articleText(newsDTO.articleText())
                .imgData(newsDTO.imgData())
                .build();
        newsRepository.saveAndFlush(updated);

        return Response.successful("Updated");
    }
    private void validateNewsExistence(long id, boolean validateTo){
       Optional<News> news = newsRepository.findById(id);
        if(news.isPresent()) {
            if (validateTo) {
                throw new DataIntegrityViolationException("Article with id " + id + " already exist");
            }
        }else {
            throw new NotFoundException("News");
        }
    }
}
