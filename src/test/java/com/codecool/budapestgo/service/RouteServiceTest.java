package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.route.RouteDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.Route;
import com.codecool.budapestgo.dao.repository.RouteRepository;
import com.codecool.budapestgo.dao.types.TransporterCategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @Test
    void testGetAllRoutesReturnsEmptyListWhenRoutesNotExist() {
        List<Route> routes = new ArrayList<>();
        when(routeRepository.findAll()).thenReturn(routes);

        List<RouteDTO> routeDTOs = routeService.getAllRoutes();

        assertEquals(0, routeDTOs.size());
    }

    @Test
    void testDeleteRoute() {
        Long id = 1L;
        routeService.deleteRoute(id);
        verify(routeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteAllRoutesSuccess() {
        routeService.deleteAllRoutes();
        verify(routeRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteAllRoutesFailure() {
        doThrow(new RuntimeException()).when(routeRepository).deleteAll();

        ResponseEntity<String> response = routeService.deleteAllRoutes();

        assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
        assertEquals("Routes couldn't be deleted", response.getBody());
    }
    @Test
    void testGetRouteByIdWhenFound() {
        Long id = 1L;
        Route route = buildRoute(id, "Route", TransporterCategoryType.BUS);
        when(routeRepository.getRouteById(id)).thenReturn(Optional.of(route));

        Route foundRoute = routeService.getRouteById(id);

        assertEquals(route, foundRoute);
    }

    @Test
    void testGetRouteByIdWhenNotFound() {
        Long id = 1L;
        when(routeRepository.getRouteById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> routeService.getRouteById(id));
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
