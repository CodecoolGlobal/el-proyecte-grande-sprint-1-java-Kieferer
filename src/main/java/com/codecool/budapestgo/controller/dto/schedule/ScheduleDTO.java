package com.codecool.budapestgo.controller.dto.schedule;

import com.codecool.budapestgo.dao.model.Schedule;
import jakarta.validation.constraints.NotNull;

public record ScheduleDTO(@NotNull @Min(1) Long routeId,@NotNull @Min(1) Long stopId) {
    public static ScheduleDTO of(Schedule schedule){
        return new ScheduleDTO(schedule.getRoute().getId(), schedule.getStop().getId());
    }
}
