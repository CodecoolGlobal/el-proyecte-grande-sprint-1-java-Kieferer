package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.news.NewsDTO;
import com.codecool.budapestgo.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news/api")
public class NewsController {
    private final NewsService newsService;
    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    @GetMapping("/all")
    public List<NewsDTO> getAllNews(){
        return newsService.getAllNews();
    }
    @GetMapping("/{title}")
    public NewsDTO getNewsByTitle(@PathVariable String title){
        return newsService.getNewsByTitle(title);
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerNews(@Valid @RequestBody NewsDTO newsDTO){
        return newsService.addNews(newsDTO);
    }
    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteNewsByTitle(@PathVariable String title){
        return newsService.deleteNews(title);
    }
    @PutMapping
    public ResponseEntity<String> updateNewsByTitle(@Valid @RequestBody NewsDTO newsDTO){
        return newsService.updateNews(newsDTO);
    }
}
