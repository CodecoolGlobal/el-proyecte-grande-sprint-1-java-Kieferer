package com.codecool.budapestgo.controller.dto.stop;

import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.data.Point;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StopDTO(@NotNull @NotBlank String name,@NotNull double latitude,@NotNull double longitude) {
    public static StopDTO of(Stop stop){
        Point location = stop.getLocation();
        return new StopDTO(stop.getName(), location.getLatitude(), location.getLongitude());
    }
}
