package com.codecool.budapestgo.controller.dto.route;

import com.codecool.budapestgo.dao.model.Route;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewRouteDTO(@NotNull @NotBlank String name, @NotNull @NotBlank String category) {
    public static NewRouteDTO of(Route route){
        return new NewRouteDTO(route.getName(),route.getCategoryType().toString());
    }
}
