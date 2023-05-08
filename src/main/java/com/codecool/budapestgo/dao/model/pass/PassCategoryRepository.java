package com.codecool.budapestgo.dao.model.pass;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PassCategoryRepository extends JpaRepository<PassCategory,Integer> {
    Optional<PassCategory> findByCategoryAndPassDuration(String passDuration, String category);
}
