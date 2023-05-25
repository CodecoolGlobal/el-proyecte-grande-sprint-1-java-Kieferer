package com.codecool.budapestgo.controller.dto.pass;


import com.codecool.budapestgo.dao.model.PurchasedPass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PassDTO(@NotNull @NotBlank String email, @NotNull @NotBlank String passDuration,@NotNull @NotBlank String passCategory) {
    public static PassDTO of(PurchasedPass pass){
        return new PassDTO(pass.getClient().getEmail(),pass.getPassCategory().getPassDuration() ,pass.getPassCategory().getCategory());
    }
}
