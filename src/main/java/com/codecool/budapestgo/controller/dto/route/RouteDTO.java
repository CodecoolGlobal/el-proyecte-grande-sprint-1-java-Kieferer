package com.codecool.budapestgo.controller.dto.route;

import com.codecool.budapestgo.dao.Route.Route;

import java.util.Objects;

public final class RouteDTO {
    private final int id;
    private final String name;

    public RouteDTO(Route route) {
        this.id = route.getId();
        this.name = route.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RouteDTO) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "RouteDTO[" +
                "id=" + id + ", " +
                "name=" + name + ']';
    }

}
