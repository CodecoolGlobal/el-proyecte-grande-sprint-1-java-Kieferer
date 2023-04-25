package com.codecool.budapestgo.controller.dto;

import com.codecool.budapestgo.dao.Route.Route;

import java.util.Objects;

public final class RouteDTO {
    private final String name;

    public RouteDTO(Route route) {
        this.name = route.getName();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RouteDTO) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "RouteDTO[" +
                "name=" + name + ']';
    }

}
