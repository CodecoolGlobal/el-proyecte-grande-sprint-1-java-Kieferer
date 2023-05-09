package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassRepository extends JpaRepository<Pass, Long> {
    @Query("SELECT p FROM Pass p WHERE p.expireTime < NOW() AND  p.client.id = :id")
    List<Pass> getAllExpiredPassesByClient_id(Long id);
    @Query("SELECT p FROM Pass p WHERE p.expireTime > NOW() AND  p.client.id = :id")
    List<Pass> getActivePassesByClient_id(Long id);
}
