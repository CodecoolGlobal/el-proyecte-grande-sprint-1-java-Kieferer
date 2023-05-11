package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.route.NewRouteDTO;
import com.codecool.budapestgo.controller.dto.route.RouteDTO;
import com.codecool.budapestgo.controller.dto.route.UpdateRouteDTO;
import com.codecool.budapestgo.service.RouteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    @PutMapping("/update")
    public ResponseEntity<String> updateRoute(@Valid @RequestBody UpdateRouteDTO updateRouteDTO){
        return routeService.updateRoute(updateRouteDTO);
    }

    @GetMapping("/all")
    public List<RouteDTO> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@Valid @PathVariable @Min(1) Long id){
        routeService.deleteRoute(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoute(@Valid @RequestBody NewRouteDTO newRoute) {
                return routeService.addRoute(newRoute);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllRoutes(){
        return  routeService.deleteAllRoutes();
    }
}
