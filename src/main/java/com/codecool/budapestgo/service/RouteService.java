package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.route.NewRouteDTO;
import com.codecool.budapestgo.controller.dto.route.RouteDTO;
import com.codecool.budapestgo.controller.dto.route.UpdateRouteDTO;
import com.codecool.budapestgo.dao.model.Route;
import com.codecool.budapestgo.dao.repository.RouteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll().stream().map(RouteDTO::of).toList();
    }


    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public void addRoute(NewRouteDTO newRouteDTO) {
        Route route = Route.builder()
                .name(newRouteDTO.name())
                .build();
        routeRepository.save(route);
    }

    public ResponseEntity<String> updateRoute(UpdateRouteDTO newRouteDTO) {
        try {
            Route route = Route.builder()
                    .id(newRouteDTO.id())
                    .name(newRouteDTO.name())
                    .build();
            routeRepository.save(route);
            return ResponseEntity.ok("Route updated");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Route not found");
        }
    }
}
