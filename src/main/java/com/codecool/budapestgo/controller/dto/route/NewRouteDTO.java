package com.codecool.budapestgo.controller.dto.route;

import com.codecool.budapestgo.dao.model.Route;

public record NewRouteDTO(String name) {
    public static NewRouteDTO of(Route route){
        return new NewRouteDTO(route.getName());
    }
}
