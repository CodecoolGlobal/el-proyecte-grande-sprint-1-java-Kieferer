package com.codecool.budapestgo.controller.dto.route;

import com.codecool.budapestgo.dao.model.Route;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateRouteDTO(@NotNull @Min(1) Long id, @NotNull @NotBlank String name) {
    public static UpdateRouteDTO of(Route route){
        return new UpdateRouteDTO(route.getId(), route.getName());
    }
}
