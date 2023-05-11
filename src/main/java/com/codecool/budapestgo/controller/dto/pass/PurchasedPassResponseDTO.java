package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.PurchasedPass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PurchasedPassResponseDTO(@NotNull @NotBlank String passType,@NotNull @NotBlank LocalDate expirationDate,@NotNull @NotBlank String category,@NotNull int price) {

    public static PurchasedPassResponseDTO of(PurchasedPass purchasedPass) {
        return new PurchasedPassResponseDTO(purchasedPass.getPassCategory().getPassDuration(),
                purchasedPass.getExpireTime(),
                purchasedPass.getPassCategory().getCategory(),
                purchasedPass.getPassCategory().getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasedPassResponseDTO that = (PurchasedPassResponseDTO) o;
        return price == that.price && passType.equals(that.passType) && expirationDate.equals(that.expirationDate) && category.equals(that.category);
    }

}
