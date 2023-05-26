package com.codecool.budapestgo.controller.dto.route;

import com.codecool.budapestgo.dao.model.Route;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RouteDTO(@NotNull @Min(1) Long id, @NotNull @NotBlank String name,@NotNull @NotBlank String category ) {
    public static RouteDTO of(Route route){
        return new RouteDTO(route.getId(), route.getName(), route.getCategoryType().toString());
    }
}
