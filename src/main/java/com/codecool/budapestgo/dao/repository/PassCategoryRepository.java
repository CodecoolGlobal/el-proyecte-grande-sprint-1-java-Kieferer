package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.PassCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PassCategoryRepository extends JpaRepository<PassCategory,Long> {
    Optional<PassCategory> findByCategoryAndPassDuration(String category,String passDuration);
}
