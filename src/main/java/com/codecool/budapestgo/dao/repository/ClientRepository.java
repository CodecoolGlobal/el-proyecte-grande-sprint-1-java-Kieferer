package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByEmail(String email);
    void deleteByEmail(String email);
}
