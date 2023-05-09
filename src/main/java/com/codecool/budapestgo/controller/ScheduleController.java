package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.schedule.ScheduleDTO;
import com.codecool.budapestgo.controller.dto.validator.DTOValidator;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    @PostMapping("/add")
    public ResponseEntity<String> addSchedule(@RequestBody ScheduleDTO scheduleDTO){
        try {
            if (DTOValidator.registrationIsInvalid(scheduleDTO)) {
                return ResponseEntity.badRequest().body("Fields cannot be empty");
            } else {
                return scheduleService.addSchedule(scheduleDTO);
            }
        }catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
        }
    }
    @GetMapping("/{name}")
    public List<Schedule> getScheduleByRouteId(@PathVariable String name){
        return scheduleService.getRouteSchedule(name);
    }
    @GetMapping("/stops/{name}")
    public List<Stop> getStopsOfRouteByName(@PathVariable String name){
        return scheduleService.getStopsOfRouteByName(name);
    }
    @GetMapping("/all")
    public List<Schedule> getAllSchedule(){
        return scheduleService.getAllSchedule();
    }
    @DeleteMapping("/{id}")
    public void deleteScheduleById(@PathVariable Long id){
        scheduleService.deleteScheduleById(id);
    }
}
