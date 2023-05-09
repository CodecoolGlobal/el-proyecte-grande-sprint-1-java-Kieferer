package com.codecool.budapestgo.controller.dto.route;

import com.codecool.budapestgo.dao.model.Route;

public record UpdateRouteDTO(Long id, String name) {
    public static UpdateRouteDTO of(Route route){
        return new UpdateRouteDTO(route.getId(), route.getName());
    }
}
