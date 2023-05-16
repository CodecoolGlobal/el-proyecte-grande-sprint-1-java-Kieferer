package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.route.NewRouteDTO;
import com.codecool.budapestgo.controller.dto.route.RouteDTO;
import com.codecool.budapestgo.controller.dto.route.UpdateRouteDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.Route;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.repository.RouteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ResponseEntity<String> addRoute(NewRouteDTO newRouteDTO) {
            Route route = Route.builder()
                    .name(newRouteDTO.name())
                    .build();
            routeRepository.save(route);
            return ResponseEntity.ok("Route created");
    }

    public ResponseEntity<String> updateRoute(UpdateRouteDTO updateRouteDTO) {
        Route route = routeRepository.getRouteById(updateRouteDTO.id()).orElseThrow(() -> new NotFoundException("Route with ID " + updateRouteDTO.id()));
        List<Schedule> schedules = new ArrayList<>(route.getSchedules());
        schedules.forEach(route::removeSchedule);

        Route updatedRoute = Route.builder()
                .id(updateRouteDTO.id())
                .name(updateRouteDTO.name())
                .schedules(schedules)
                .build();

        schedules.forEach(updatedRoute::addSchedule);

        routeRepository.save(updatedRoute);
        return ResponseEntity.ok("Route updated");
    }

    public ResponseEntity<String> deleteAllRoutes() {
        try {
            routeRepository.deleteAll();
            return ResponseEntity.ok("All route deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Routes couldn't be deleted");
        }
    }
}
