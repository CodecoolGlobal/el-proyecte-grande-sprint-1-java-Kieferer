package com.codecool.budapestgo.dao.model.route;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route,Integer> {
    Optional<Route> getRouteByName(String name);
}
