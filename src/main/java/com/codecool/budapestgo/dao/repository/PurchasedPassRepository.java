package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.PurchasedPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchasedPassRepository extends JpaRepository<PurchasedPass,Long> {
    @Query("SELECT p FROM PurchasedPass p WHERE p.expireTime < NOW() AND  p.client.id = :id")
    List<PurchasedPass> getAllExpiredPassesByClient_id(Long id);
    @Query("SELECT p FROM PurchasedPass p WHERE p.expireTime > NOW() AND  p.client.id = :id")
    List<PurchasedPass> getActivePassesByClient_id(Long id);
    int deleteAllByClient_Id(Long id);
}
