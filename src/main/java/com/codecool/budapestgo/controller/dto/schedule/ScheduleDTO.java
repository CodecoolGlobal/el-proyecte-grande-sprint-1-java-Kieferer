package com.codecool.budapestgo.controller.dto.schedule;

import com.codecool.budapestgo.dao.model.Schedule;

public record ScheduleDTO(Long routeId, Long stopId) {
    public static ScheduleDTO of(Schedule schedule){
        return new ScheduleDTO(schedule.getRoute().getId(), schedule.getStop().getId());
    }
}
