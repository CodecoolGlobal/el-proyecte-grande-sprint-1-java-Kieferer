package com.codecool.budapestgo.controller.dto.pass;


import com.codecool.budapestgo.dao.model.PurchasedPass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PassDTO(@NotNull @Min(1) Long clientId, @NotNull @NotBlank String passDuration,@NotNull @NotBlank String passCategory) {
    public static PassDTO of(PurchasedPass pass){
        return new PassDTO(pass.getId(),pass.getPassCategory().getPassDuration() ,pass.getPassCategory().getCategory());
    }
}
