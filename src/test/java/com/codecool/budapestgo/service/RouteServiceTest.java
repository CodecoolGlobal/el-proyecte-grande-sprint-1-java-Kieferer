package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.route.RouteDTO;
import com.codecool.budapestgo.dao.model.Route;
import com.codecool.budapestgo.dao.repository.RouteRepository;
import com.codecool.budapestgo.dao.types.TransporterCategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteServiceTest {
    @Mock
    private RouteRepository routeRepository;

    private RouteService routeService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        routeService = new RouteService(routeRepository);
    }

    @Test
    void testGetAllRoutesReturnsAllRoutesWhenRoutesExist() {
        List<Route> routes = new ArrayList<>();
        Route routeOne = buildRoute(1L, "Route 1", TransporterCategoryType.BUS);
        Route routeTwo = buildRoute(2L, "Route 2", TransporterCategoryType.TRAM);
        routes.add(routeOne);
        routes.add(routeTwo);
        when(routeRepository.findAll()).thenReturn(routes);

        List<RouteDTO> routeDTOs = routeService.getAllRoutes();

        assertEquals(routes.size(), routeDTOs.size());

        for (int i = 0; i < routes.size(); i++) {
            assertRouteEquals(routes.get(i), routeDTOs.get(i));
        }
    }

    private Route buildRoute(Long id, String name, TransporterCategoryType categoryType) {
        return Route.builder()
                .id(id)
                .name(name)
                .categoryType(categoryType)
                .schedules(new ArrayList<>())
                .build();
    }

    private void assertRouteEquals(Route route, RouteDTO routeDTO) {
        assertEquals(route.getName(), routeDTO.name());
        assertEquals(route.getCategoryType().name(), routeDTO.category());
    }

}
