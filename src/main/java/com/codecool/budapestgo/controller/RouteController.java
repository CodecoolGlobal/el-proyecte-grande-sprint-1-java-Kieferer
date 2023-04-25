package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.NewRouteDTO;
import com.codecool.budapestgo.controller.dto.RouteDTO;
import com.codecool.budapestgo.controller.dto.UpdateRouteDTO;
import com.codecool.budapestgo.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public List<RouteDTO> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable int id){
        routeService.deleteRoute(id);
    }

    @PostMapping("/add")
    public void addRoute(@RequestBody NewRouteDTO newRoute){
        routeService.addRoute(newRoute);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateRoute(@RequestBody UpdateRouteDTO newRouteDTO){
        return routeService.updateRoute(newRouteDTO);
    }
}
