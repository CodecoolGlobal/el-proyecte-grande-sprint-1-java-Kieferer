package com.codecool.budapestgo.controller.dto.pass;


import com.codecool.budapestgo.controller.dto.route.NewRouteDTO;
import com.codecool.budapestgo.dao.model.Pass;

public record PassDTO(Long clientId, String passType) {
    public static PassDTO of(Pass pass){
        return new PassDTO(pass.getId(), pass.getPassType().name());
    }
}
