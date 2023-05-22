package com.codecool.budapestgo.controller.dto.stop;

import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.data.Point;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewStopDTO(@NotNull @NotBlank String name, @NotNull double latitude, @NotNull double longitude) {
    public static NewStopDTO of(Stop stop){
        Point location = stop.getLocation();
        return new NewStopDTO(stop.getName(), location.getLatitude(), location.getLongitude());
    }
}
