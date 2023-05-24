package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News,Long> {
    Optional<News> findByTitle(String title);
    void deleteByTitle(String title);
}
