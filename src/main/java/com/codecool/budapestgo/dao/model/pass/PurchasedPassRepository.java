package com.codecool.budapestgo.dao.model.pass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchasedPassRepository extends JpaRepository<PurchasedPass,Integer> {
    @Query("SELECT p FROM PurchasedPass p WHERE p.expireTime < NOW() AND  p.client.id = :id")
    List<PurchasedPass> getAllExpiredPassesByClient_id(Integer id);
    @Query("SELECT p FROM PurchasedPass p WHERE p.expireTime > NOW() AND  p.client.id = :id")
    List<PurchasedPass> getActivePassesByClient_id(Integer id);
    boolean deleteAllByClient_Id(Integer id);
}
