package com.codecool.budapestgo.controller.dto.pass;


import com.codecool.budapestgo.dao.model.PurchasedPass;

public record PassDTO(Long clientId, String passDuration, String passCategory) {
    public static PassDTO of(PurchasedPass pass){
        return new PassDTO(pass.getId(),pass.getPassCategory().getPassDuration() ,pass.getPassCategory().getCategory());
    }
}
