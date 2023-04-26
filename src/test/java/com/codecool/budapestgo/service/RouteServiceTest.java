package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.RouteDTO;
import com.codecool.budapestgo.dao.Route.Route;
import com.codecool.budapestgo.dao.Route.RouteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RouteServiceTest {
    @Mock
    private RouteRepository routeRepository;

    private RouteService routeService;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        routeService = new RouteService(routeRepository);
    }

    @Test
    void getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        Route routeOne = new Route(1,"9");
        Route routeTwo = new Route(2,"M3");
        routes.add(routeOne);
        routes.add(routeTwo);
        when(routeRepository.findAll()).thenReturn(routes);

        List<RouteDTO> routeDTOS = routeService.getAllRoutes();

        assertEquals(routeDTOS.size(),routes.size());
        assertEquals(routes.get(0).getId(),routeDTOS.get(0).getId());
        assertEquals(routes.get(0).getName(),routeDTOS.get(0).getName());
        assertEquals(routes.get(1).getId(),routeDTOS.get(1).getId());
        assertEquals(routes.get(1).getName(),routeDTOS.get(1).getName());
    }

    @Test
    void deleteRoute() {
    }

    @Test
    void addRoute() {
    }

    @Test
    void updateRoute() {
    }

    @Test
    void testGetAllRoutes() {
    }

    @Test
    void testDeleteRoute() {
    }

    @Test
    void testAddRoute() {
    }

    @Test
    void testUpdateRoute() {
    }
}
