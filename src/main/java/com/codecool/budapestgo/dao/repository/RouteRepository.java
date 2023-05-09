package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route,Integer> {
    Optional<Route> getRouteByName(String name);
}
