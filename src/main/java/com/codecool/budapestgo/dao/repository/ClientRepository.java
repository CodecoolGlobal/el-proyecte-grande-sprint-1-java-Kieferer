package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    Optional<Client> findClientByEmail(String email);
}
