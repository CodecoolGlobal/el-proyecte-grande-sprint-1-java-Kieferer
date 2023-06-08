package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryResponseDTO;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.repository.PassCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PassCategoryServiceTest {
    @Mock
    private PassCategoryRepository passCategoryRepository;

    private PassCategoryService passCategoryService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        passCategoryService = new PassCategoryService(passCategoryRepository);
    }

    @Test
    void testGetAllPassCategory() {
        List<PassCategory> passCategories = new ArrayList<>();
        passCategories.add(buildPassCategory(1L, "Category 1", "Duration 1",3L,100));
        passCategories.add(buildPassCategory(2L, "Category 2", "Duration 2", 4L,200));
        when(passCategoryRepository.findAll()).thenReturn(passCategories);

        List<PassCategoryResponseDTO> result = passCategoryService.getAllPassCategory();

        assertEquals(passCategories.size(), result.size());
        for (int i = 0; i < passCategories.size(); i++) {
            assertPassCategoryEquals(passCategories.get(i), result.get(i));
        }
    }

    private PassCategory buildPassCategory(Long id, String category, String duration, Long expire, Integer price) {
        return PassCategory.builder()
                .id(id)
                .category(category)
                .passExpireInDay(expire)
                .passDuration(duration)
                .price(price)
                .build();
    }

    private void assertPassCategoryEquals(PassCategory passCategory, PassCategoryResponseDTO responseDTO) {
        assertEquals(passCategory.getId(), responseDTO.id());
        assertEquals(passCategory.getCategory(), responseDTO.category());
        assertEquals(passCategory.getPassDuration(), responseDTO.passDuration());
    }
}
