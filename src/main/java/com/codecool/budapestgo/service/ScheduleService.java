package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.schedule.ScheduleDTO;
import com.codecool.budapestgo.dao.model.Route;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final RouteService routeService;
    private final StopService stopService;

    public ResponseEntity<String> addSchedule(ScheduleDTO scheduleDTO){
        Route route = routeService.getRouteById(scheduleDTO.routeId());
        Stop stop = stopService.getStopById(scheduleDTO.stopId());

        Schedule routeSchedule = Schedule.builder()
                .route(route)
                .stop(stop)
                .build();

        scheduleRepository.save(routeSchedule);

        stop.addSchedule(routeSchedule);
        route.addSchedule(routeSchedule);
        return ResponseEntity.ok("Created new schedule");
    }
    public List<Schedule> getRouteSchedule(String name){
        return scheduleRepository.findByRouteName(name);
    }

    public List<ScheduleDTO> getAllSchedule() {
        return scheduleRepository.findAll().stream().map(ScheduleDTO::of).collect(Collectors.toList());
    }

    public List<Stop> getStopsOfRouteByName(String name) {
        List<Schedule> schedules = scheduleRepository.findByRouteName(name);
        return schedules.stream().map(Schedule::getStop).toList();
    }

    public void deleteScheduleById(Long id) {
        scheduleRepository.findById(id).ifPresent(schedule -> {
            schedule.getRoute().removeSchedule(schedule);
            schedule.getStop().removeSchedule(schedule);
        });
        scheduleRepository.deleteById(id);
    }

    public ResponseEntity<String> deleteScheduleByRouteId(Long routeId) {
        try {
            scheduleRepository.deleteAll(scheduleRepository.findByRouteId(routeId));
            return ResponseEntity.ok("Deleted all the schedules that had reference to given route ID");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Schedules couldn't be deleted");
        }
    }

    public ResponseEntity<String> deleteScheduleByStopId(Long stopId) {
        try {
            scheduleRepository.deleteAll(scheduleRepository.findByStopId(stopId));
            return ResponseEntity.ok("Deleted all the schedules that had reference to given route ID");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Schedules couldn't be deleted");
        }
    }

    public List<Stop> getAllAssignedStopByRouteId(Long routeId) {
        return scheduleRepository.findStopByRouteId(routeId);
    }
}
