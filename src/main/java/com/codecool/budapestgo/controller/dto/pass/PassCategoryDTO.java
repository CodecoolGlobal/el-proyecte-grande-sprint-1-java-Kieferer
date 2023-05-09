package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.PassCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PassCategoryDTO {
    private final String category;
    private final String passDuration;
    private final long passExpireInDay;
    private final int price;
    private final Long id;

    public static PassCategoryDTO of (PassCategory passCategory){
        return PassCategoryDTO.builder()
                .category(passCategory.getCategory())
                .passDuration(passCategory.getPassDuration())
                .passExpireInDay(passCategory.getPassExpireInDay())
                .price(passCategory.getPrice())
                .id(passCategory.getId())
                .build();
    }
}
