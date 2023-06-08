package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PurchasedPassResponseDTO;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.model.PurchasedPass;
import com.codecool.budapestgo.dao.repository.PurchasedPassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PurchasedPassServiceTest {
    @Mock
    private PurchasedPassRepository purchasedPassRepository;

    @Mock
    private PassCategoryService passCategoryService;

    @Mock
    private ClientService clientService;

    private PurchasedPassService purchasedPassService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        purchasedPassService = new PurchasedPassService(purchasedPassRepository, passCategoryService, clientService);
    }

    @Test
    void testGetAllPass() {
        List<PurchasedPass> purchasedPasses = new ArrayList<>();
        purchasedPasses.add(buildPurchasedPass("Category 1", LocalDate.now()));
        purchasedPasses.add(buildPurchasedPass("Category 2", LocalDate.now()));
        when(purchasedPassRepository.findAll()).thenReturn(purchasedPasses);

        List<PurchasedPassResponseDTO> result = purchasedPassService.getAllPass();

        assertEquals(purchasedPasses.size(), result.size());
        for (int i = 0; i < purchasedPasses.size(); i++) {
            assertPurchasedPassEquals(purchasedPasses.get(i), result.get(i));
        }
    }

    private PurchasedPass buildPurchasedPass(String category,LocalDate expirationDate) {
        PassCategory passCategory = buildPassCategory(category);
        return PurchasedPass.builder()
                .passCategory(passCategory)
                .expireTime(expirationDate)
                .build();
    }

    private PassCategory buildPassCategory(String category) {
        return PassCategory.builder()
                .category(category)
                .passDuration("Duration")
                .passExpireInDay(10L)
                .price(10)
                .build();
    }

    private void assertPurchasedPassEquals(PurchasedPass purchasedPass, PurchasedPassResponseDTO responseDTO) {
        assertEquals(purchasedPass.getPassCategory().getPassDuration(), responseDTO.passType());
        assertEquals(purchasedPass.getExpireTime(), responseDTO.expirationDate());
        assertEquals(purchasedPass.getPassCategory().getCategory(), responseDTO.category());
        assertEquals(purchasedPass.getPassCategory().getPrice(), responseDTO.price());
    }
}
