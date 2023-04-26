package com.codecool.budapestgo.dao.model.pass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PassRepository extends JpaRepository<Pass,Integer> {
    @Query("SELECT s FROM Pass s WHERE s.startTime < :date")
    List<Pass> getAllExpiredPasses(LocalDate date);
    @Query("SELECT s FROM Pass s WHERE s.startTime > :date")
    List<Pass> getActivePasses(LocalDate date);
}
