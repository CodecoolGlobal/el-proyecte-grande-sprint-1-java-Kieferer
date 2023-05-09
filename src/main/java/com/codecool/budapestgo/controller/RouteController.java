package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.route.NewRouteDTO;
import com.codecool.budapestgo.controller.dto.route.RouteDTO;
import com.codecool.budapestgo.controller.dto.route.UpdateRouteDTO;
import com.codecool.budapestgo.controller.dto.validator.DTOValidator;
import com.codecool.budapestgo.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
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
    public void deleteRoute(@PathVariable Long id){
        routeService.deleteRoute(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoute(@RequestBody NewRouteDTO newRoute) {
        try {
            if (DTOValidator.registrationIsInvalid(newRoute)) {
                return ResponseEntity.badRequest().body("Fields cannot be empty");
            } else {
                return routeService.addRoute(newRoute);
            }
        }catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateRoute(@RequestBody UpdateRouteDTO newRouteDTO){
        return routeService.updateRoute(newRouteDTO);
    }
}
