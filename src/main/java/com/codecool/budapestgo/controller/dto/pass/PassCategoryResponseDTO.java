package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.PassCategory;
import lombok.Builder;
@Builder
public record PassCategoryResponseDTO(String category, String passDuration, long passExpireInDay, int price, Long id) {
    public static PassCategoryResponseDTO of(PassCategory passCategory) {
        return PassCategoryResponseDTO.builder()
                .category(passCategory.getCategory())
                .passDuration(passCategory.getPassDuration())
                .passExpireInDay(passCategory.getPassExpireInDay())
                .price(passCategory.getPrice())
                .id(passCategory.getId())
                .build();
    }
}
