package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Route;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> getRouteById(@NonNull Long id);
}
