package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.route.NewRouteDTO;
import com.codecool.budapestgo.controller.dto.route.RouteDTO;
import com.codecool.budapestgo.controller.dto.route.UpdateRouteDTO;
import com.codecool.budapestgo.dao.model.Route;
import com.codecool.budapestgo.dao.repository.RouteRepository;
import com.codecool.budapestgo.dao.types.TransporterCategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testGetAllRoute() {
        List<Route> routes = new ArrayList<>();
        Route routeOne = new Route(1L,"9",TransporterCategoryType.BUS);
        Route routeTwo = new Route(2L,"M3",TransporterCategoryType.BUS);
        routes.add(routeOne);
        routes.add(routeTwo);
        when(routeRepository.findAll()).thenReturn(routes);

        List<RouteDTO> routeDTOS = routeService.getAllRoutes();

        assertEquals(routeDTOS.size(),routes.size());
        assertEquals(routes.get(0).getId(),routeDTOS.get(0).id());
        assertEquals(routes.get(0).getName(),routeDTOS.get(0).name());
        assertEquals(routes.get(1).getId(),routeDTOS.get(1).id());
        assertEquals(routes.get(1).getName(),routeDTOS.get(1).name());
    }

    @Test
    void testDeleteRoute() {
        Long id = 1L;
        routeService.deleteRoute(id);

        verify(routeRepository ,times(1)).deleteById(id);
    }

    @Test
    void testAddRoute() {
        NewRouteDTO newRouteDTO = new NewRouteDTO("9","BUS");
        routeService.addRoute(newRouteDTO);

        verify(routeRepository, times(1)).save(argThat(route -> route.getName().equals(newRouteDTO.name())));
    }

    @Test
    void testUpdateRouteWhenRouteExist() {
        Long id = 1L;
        String newName= "Updated Route";
        TransporterCategoryType categoryType = TransporterCategoryType.BUS;
        UpdateRouteDTO updateRouteDTO = new UpdateRouteDTO(id,newName,categoryType.toString());
        Route route = new Route(id,"Old Route", TransporterCategoryType.BUS);
        when(routeRepository.findById(id)).thenReturn(Optional.of(route));
        when(routeRepository.save(route)).thenReturn(route);

        ResponseEntity<String> response = routeService.updateRoute(updateRouteDTO);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Route updated", response.getBody());
        assertEquals(newName,route.getName());
    }

    @Test
    void testUpdateRouteWhenRouteNotExist() {
        Long id = 1L;
        UpdateRouteDTO updateRouteDTO = new UpdateRouteDTO(id,"9","BUS");
        when(routeRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = routeService.updateRoute(updateRouteDTO);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals("Route not found", response.getBody());
    }
}
