package com.codecool.budapestgo.controller.dto.stop;

import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.data.Point;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateStopDTO(@NotNull @Min(1) Long id, @NotNull @NotBlank String name, @NotNull double latitude, @NotNull double longitude) {
    public static UpdateStopDTO of(Stop stop){
        Point location = stop.getLocation();
        return new UpdateStopDTO(stop.getId(), stop.getName(), location.getLatitude(), location.getLongitude());
    }
}
