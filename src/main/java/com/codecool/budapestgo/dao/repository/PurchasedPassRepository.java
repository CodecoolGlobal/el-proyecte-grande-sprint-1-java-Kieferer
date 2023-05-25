package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.PurchasedPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchasedPassRepository extends JpaRepository<PurchasedPass,Long> {
    @Query("SELECT p FROM PurchasedPass p WHERE p.expireTime < NOW() AND  p.client.email = :email")
    List<PurchasedPass> getAllExpiredPassesByClient(String email);
    @Query("SELECT p FROM PurchasedPass p WHERE p.expireTime > NOW() AND  p.client.email = :email")
    List<PurchasedPass> getActivePassesByClient(String email);
    @Query("SELECT p FROM PurchasedPass p WHERE p.client.email = :email")
    List<PurchasedPass> findAllByByClient(String email);
    int deleteAllByClient_Email(String email);
}
