package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.schedule.ScheduleDTO;
import com.codecool.budapestgo.dao.model.route.Route;
import com.codecool.budapestgo.dao.model.route.RouteRepository;
import com.codecool.budapestgo.dao.model.schedule.Schedule;
import com.codecool.budapestgo.dao.model.schedule.ScheduleRepository;
import com.codecool.budapestgo.dao.model.stop.Stop;
import com.codecool.budapestgo.dao.model.stop.StopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    RouteRepository routeRepository;
    StopRepository stopRepository;
    ScheduleRepository scheduleRepository;

    public ScheduleService(RouteRepository routeRepository, StopRepository stopRepository, ScheduleRepository scheduleRepository) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public void addSchedule(ScheduleDTO scheduleDTO){
        Optional<Route> route = routeRepository.getRouteByName(scheduleDTO.routeName());
        Optional<Stop> stop = stopRepository.getStopByName(scheduleDTO.stopName());

        if (route.isEmpty() || stop.isEmpty())
            throw new RuntimeException("There is no matching route or stop");

        Schedule routeSchedule = Schedule.builder()
                .route(route.get())
                .stop(stop.get())
                .build();
        scheduleRepository.save(routeSchedule);
    }
    public List<Schedule> getRouteSchedule(String name){
        return scheduleRepository.findByRouteName(name);
    }

    public List<Schedule> getAllSchedule() {
        return scheduleRepository.findAll();
    }
}
