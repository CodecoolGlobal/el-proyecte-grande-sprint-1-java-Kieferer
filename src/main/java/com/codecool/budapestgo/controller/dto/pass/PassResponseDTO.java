package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.Pass;
import com.codecool.budapestgo.dao.types.PassType;

import java.time.LocalDate;
public record PassResponseDTO(String passType, LocalDate startDate, LocalDate expirationDate) {
    public static PassResponseDTO of(Pass pass){
        return new PassResponseDTO(pass.getPassType().name(), pass.getStartTime(), pass.getExpireTime());
    }
}
