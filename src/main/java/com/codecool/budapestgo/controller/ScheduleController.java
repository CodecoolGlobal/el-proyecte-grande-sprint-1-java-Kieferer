package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.schedule.ScheduleDTO;
import com.codecool.budapestgo.dao.model.schedule.Schedule;
import com.codecool.budapestgo.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    @PostMapping("/add")
    public void addSchedule(@RequestBody ScheduleDTO scheduleDTO){
        scheduleService.addSchedule(scheduleDTO);
    }
    @GetMapping("/{name}")
    public List<Schedule> getScheduleByRouteId(@PathVariable String name){
        return scheduleService.getRouteSchedule(name);
    }
    @GetMapping("/all")
    public List<Schedule> getAllSchedule(){
        return scheduleService.getAllSchedule();
    }
}
