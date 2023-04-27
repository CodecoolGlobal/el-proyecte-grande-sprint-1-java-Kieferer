package com.codecool.budapestgo.dao.model.pass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PassRepository extends JpaRepository<Pass,Integer> {
    @Query("SELECT p FROM Pass p WHERE p.expireTime < :date AND  p.client.id = :id")
    List<Pass> getAllExpiredPassesByClient_id(Integer id,LocalDate date);
    @Query("SELECT p FROM Pass p WHERE p.expireTime > :date AND  p.client.id = :id")
    List<Pass> getActivePassesByClient_id(Integer id,LocalDate date);
}
