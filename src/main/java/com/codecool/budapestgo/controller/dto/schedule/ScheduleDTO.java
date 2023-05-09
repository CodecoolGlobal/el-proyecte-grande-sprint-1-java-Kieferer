package com.codecool.budapestgo.controller.dto.schedule;

import com.codecool.budapestgo.dao.model.Schedule;

public record ScheduleDTO(String routeName, String stopName) {
    public static ScheduleDTO of(Schedule schedule){
        return new ScheduleDTO(schedule.getRoute().getName(), schedule.getStop().getName());
    }
}
