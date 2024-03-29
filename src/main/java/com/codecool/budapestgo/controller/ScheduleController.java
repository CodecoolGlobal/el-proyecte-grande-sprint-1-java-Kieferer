package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.schedule.ScheduleDTO;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.service.ScheduleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/add")
    public ResponseEntity<String> addSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO){
                return scheduleService.addSchedule(scheduleDTO);
    }
    @GetMapping("/{name}")
    public List<Schedule> getScheduleByRouteId(@Valid @PathVariable @NotBlank String name){
        return scheduleService.getRouteSchedule(name);
    }
    @GetMapping("/stops/{name}")
    public List<Stop> getStopsOfRouteByName(@Valid @PathVariable @NotBlank String name){
        return scheduleService.getStopsOfRouteByName(name);
    }
    @GetMapping("/all")
    public List<ScheduleDTO> getAllSchedule(){
        return scheduleService.getAllSchedule();
    }
    @DeleteMapping("/{id}")
    public void deleteScheduleById(@Valid @PathVariable @Min(1) Long id){
        scheduleService.deleteScheduleById(id);
    }
    @DeleteMapping("/connected-to-route-id/{routeId}")
    public ResponseEntity<String> deleteScheduleByRouteId(@PathVariable Long routeId){
        return scheduleService.deleteScheduleByRouteId(routeId);
    }
    @DeleteMapping("/connected-to-stop-id/{stopId}")
    public ResponseEntity<String> deleteScheduleByStopId(@PathVariable Long stopId){
        return scheduleService.deleteScheduleByStopId(stopId);
    }
    @GetMapping("/stops-connected-to-route-id/{routeId}")
    public List<Stop> getAllAssignedStopByRouteId(@PathVariable Long routeId){
        return scheduleService.getAllAssignedStopByRouteId(routeId);
    }
}
