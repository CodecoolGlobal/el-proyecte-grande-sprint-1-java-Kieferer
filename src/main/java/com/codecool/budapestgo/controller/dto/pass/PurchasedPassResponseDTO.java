package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.pass.PurchasedPass;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;
@Getter
public final class PurchasedPassResponseDTO {
    private final String passType;
    private final String category;
    private final int price;
    private final LocalDate expirationDate;

    public PurchasedPassResponseDTO(String passType, LocalDate expirationDate, String category, int price) {
        this.passType = passType;
        this.expirationDate = expirationDate;
        this.category = category;
        this.price = price;
    }

    public static PurchasedPassResponseDTO of(PurchasedPass purchasedPass){
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

    @Override
    public int hashCode() {
        return Objects.hash(passType, expirationDate, category, price);
    }
}
