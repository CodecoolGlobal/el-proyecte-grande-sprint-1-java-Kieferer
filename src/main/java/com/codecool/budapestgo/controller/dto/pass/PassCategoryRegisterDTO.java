package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.PassCategory;
import lombok.Builder;

@Builder
public record PassCategoryRegisterDTO(String category, String passDuration, long passExpireInDay, int price) {
    public static PassCategoryRegisterDTO of(PassCategory passCategory) {
        return PassCategoryRegisterDTO.builder()
                .category(passCategory.getCategory())
                .passDuration(passCategory.getPassDuration())
                .passExpireInDay(passCategory.getPassExpireInDay())
                .price(passCategory.getPrice())
                .build();
    }
}
