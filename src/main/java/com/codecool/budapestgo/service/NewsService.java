package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.news.NewsDTO;
import com.codecool.budapestgo.controller.dto.news.NewsUpdateDTO;
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
        validateNewsExistence(newsDTO.title());
        News newNews = DtoMapper.toEntity(newsDTO);
        newsRepository.save(newNews);

        return Response.successful("Created");
    }
    public ResponseEntity<String> deleteNews(long id){
        validateNewsExistence(id);
        newsRepository.deleteById(id);

        return Response.successful("Deleted");
    }
    public ResponseEntity<String> updateNews(NewsUpdateDTO newsDTO){
        validateNewsExistence(newsDTO.id());
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
    private void validateNewsExistence(long id){
       Optional<News> news = newsRepository.findById(id);
        if(news.isEmpty()) {
            throw new NotFoundException("News");
        }
    }
    private void validateNewsExistence(String title){
        Optional<News> news = newsRepository.findByTitle(title);
        if(news.isPresent()) {
                throw new DataIntegrityViolationException("Article with id " + title + " already exist");
            }
        }

}
