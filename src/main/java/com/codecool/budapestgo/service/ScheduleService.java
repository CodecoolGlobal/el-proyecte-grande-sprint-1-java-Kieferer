package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.schedule.ScheduleDTO;
import com.codecool.budapestgo.dao.model.Route;
import com.codecool.budapestgo.dao.repository.RouteRepository;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.repository.ScheduleRepository;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.repository.StopRepository;
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

    public List<Stop> getStopsOfRouteByName(String name) {
        List<Schedule> schedules = scheduleRepository.findByRouteName(name);
        return schedules.stream().map(Schedule::getStop).toList();
    }

    public void deleteScheduleById(int id) {
        scheduleRepository.deleteById(id);
    }

    public void deleteSchedulesByRouteName(String routeName) {
        scheduleRepository.deleteAll(scheduleRepository.findByRouteName(routeName));
    }
}
