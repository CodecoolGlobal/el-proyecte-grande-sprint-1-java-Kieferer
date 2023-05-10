package com.codecool.budapestgo.controller.dto.schedule;

import com.codecool.budapestgo.dao.model.Schedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScheduleDTO(@NotNull @NotBlank String routeName,@NotNull @NotBlank String stopName) {
    public static ScheduleDTO of(Schedule schedule){
        return new ScheduleDTO(schedule.getRoute().getName(), schedule.getStop().getName());
    }
}
