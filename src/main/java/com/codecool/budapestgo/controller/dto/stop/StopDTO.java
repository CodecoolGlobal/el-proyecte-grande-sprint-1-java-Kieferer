package com.codecool.budapestgo.controller.dto.stop;

import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.data.Point;

public record StopDTO(String name, double latitude, double longitude) {
    public static StopDTO of(Stop stop){
        Point location = stop.getLocation();
        return new StopDTO(stop.getName(), location.getLatitude(), location.getLongitude());
    }
}
