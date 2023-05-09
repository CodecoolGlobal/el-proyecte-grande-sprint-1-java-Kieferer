package com.codecool.budapestgo.controller.dto.route;

import com.codecool.budapestgo.dao.model.Route;

import java.util.Objects;

public record RouteDTO(Long id, String name) {
    public static RouteDTO of(Route route){
        return new RouteDTO(route.getId(), route.getName());
    }
}
