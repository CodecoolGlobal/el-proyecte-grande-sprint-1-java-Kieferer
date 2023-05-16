package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.PassCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PassCategoryRegisterDTO(@NotNull @NotBlank String category, @NotNull @NotBlank String passDuration, @NotNull @Min(1) long passExpireInDay,@NotNull int price) {
    public static PassCategoryRegisterDTO of(PassCategory passCategory) {
        return PassCategoryRegisterDTO.builder()
                .category(passCategory.getCategory())
                .passDuration(passCategory.getPassDuration())
                .passExpireInDay(passCategory.getPassExpireInDay())
                .price(passCategory.getPrice())
                .build();
    }
}
