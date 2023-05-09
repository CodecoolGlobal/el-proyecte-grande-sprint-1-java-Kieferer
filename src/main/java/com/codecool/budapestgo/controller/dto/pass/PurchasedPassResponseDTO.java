package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.PurchasedPass;

import java.time.LocalDate;

public record PurchasedPassResponseDTO(String passType, LocalDate expirationDate, String category, int price) {

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
