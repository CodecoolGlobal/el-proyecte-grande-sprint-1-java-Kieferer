package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.schedule.ScheduleDTO;
import com.codecool.budapestgo.dao.model.Route;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.repository.ScheduleRepository;
import com.codecool.budapestgo.dao.types.TransporterCategoryType;
import com.codecool.budapestgo.data.Point;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private RouteService routeService;

    @Mock
    private StopService stopService;

    private ScheduleService scheduleService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        scheduleService = new ScheduleService(scheduleRepository, routeService, stopService);
    }

    @Test
    void testAddSchedule() {
        Long routeId = 1L;
        Long stopId = 1L;
        ScheduleDTO scheduleDTO = new ScheduleDTO(routeId, stopId);

        Route route = buildRoute(routeId, "Route 1", TransporterCategoryType.BUS);
        Stop stop = buildStop(stopId, "Stop 1", new Point(1.0, 2.0));

        when(routeService.getRouteById(routeId)).thenReturn(route);
        when(stopService.getStopById(stopId)).thenReturn(stop);

        ResponseEntity<String> response = scheduleService.addSchedule(scheduleDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Created successfully", response.getBody());
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    void testGetRouteSchedule() {
        String routeName = "Route 1";
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(
                buildSchedule(1L,
                        buildRoute(1L, routeName, TransporterCategoryType.METRO),
                        buildStop(1L, "Stop A", new Point(1.2, 3.4))));
        when(scheduleRepository.findByRouteName(routeName)).thenReturn(schedules);

        List<Schedule> result = scheduleService.getRouteSchedule(routeName);

        assertEquals(schedules.size(), result.size());
        assertEquals(schedules, result);
    }

    @Test
    void testGetAllSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(
                buildSchedule(1L,
                        buildRoute(1L, "Route 1", TransporterCategoryType.BUS),
                        buildStop(1L, "Stop 1", new Point(2.1, 0.1))));
        when(scheduleRepository.findAll()).thenReturn(schedules);

        List<ScheduleDTO> result = scheduleService.getAllSchedule();

        assertEquals(schedules.size(), result.size());
        for (int i = 0; i < schedules.size(); i++) {
            assertScheduleEquals(schedules.get(i), result.get(i));
        }
    }

    @Test
    void testGetStopsOfRouteByName() {
        String routeName = "Route 1";
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(
                buildSchedule(1L,
                        buildRoute(1L, routeName, TransporterCategoryType.METRO),
                        buildStop(1L, "Stop 1", new Point())));
        when(scheduleRepository.findByRouteName(routeName)).thenReturn(schedules);

        List<Stop> result = scheduleService.getStopsOfRouteByName(routeName);

        assertEquals(schedules.size(), result.size());
        assertEquals(schedules.get(0).getStop(), result.get(0));
    }

    @Test
    void testDeleteScheduleById() {
        Long scheduleId = 1L;
        Schedule schedule = buildSchedule(scheduleId,
                buildRoute(1L, "Route 1", TransporterCategoryType.METRO),
                buildStop(1L, "Stop 1", new Point(2.0, 1.0)));
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        scheduleService.deleteScheduleById(scheduleId);

        verify(scheduleRepository, times(1)).deleteById(scheduleId);
    }

    @Test
    void testDeleteScheduleByRouteId() {
        Long routeId = 1L;
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(
                buildSchedule(1L,
                        buildRoute(routeId, "Route 1",TransporterCategoryType.METRO),
                        buildStop(1L, "Stop 1",new Point(1.0,2.0))));
        when(scheduleRepository.findByRouteId(routeId)).thenReturn(schedules);

        ResponseEntity<String> response = scheduleService.deleteScheduleByRouteId(routeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted all the schedules that had reference to given route ID", response.getBody());
        verify(scheduleRepository, times(1)).deleteAll(schedules);
    }

    private Route buildRoute(Long id, String name, TransporterCategoryType transporterCategoryType) {
        return Route.builder()
                .id(id)
                .name(name)
                .categoryType(transporterCategoryType)
                .schedules(new ArrayList<>())
                .build();
    }

    private Stop buildStop(Long id, String name, Point point) {
        return Stop.builder()
                .id(id)
                .name(name)
                .location(point)
                .schedules(new ArrayList<>())
                .build();
    }

    private Schedule buildSchedule(Long id, Route route, Stop stop) {
        return Schedule.builder()
                .id(id)
                .route(route)
                .stop(stop)
                .build();
    }

    private void assertScheduleEquals(Schedule schedule, ScheduleDTO scheduleDTO) {
        assertEquals(schedule.getRoute().getId(), scheduleDTO.routeId());
        assertEquals(schedule.getStop().getId(), scheduleDTO.stopId());
    }
}
