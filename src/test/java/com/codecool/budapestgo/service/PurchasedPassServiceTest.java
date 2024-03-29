package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PurchasedPassResponseDTO;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.model.PurchasedPass;
import com.codecool.budapestgo.dao.repository.PurchasedPassRepository;
import com.codecool.budapestgo.utils.DtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Test
    void testGetExpiredPasses() {
        String email = "test@example.com";
        List<PurchasedPass> expiredPasses = new ArrayList<>();
        expiredPasses.add(
                buildPurchasedPass("Pass 1", LocalDate.now().minusDays(1)));
        expiredPasses.add(
                buildPurchasedPass("Pass 2", LocalDate.now().minusDays(1)));
        when(purchasedPassRepository.getAllExpiredPassesByClient(email)).thenReturn(expiredPasses);

        List<PurchasedPassResponseDTO> result = purchasedPassService.getExpiredPasses(email);

        assertEquals(expiredPasses.size(), result.size());
        for (int i = 0; i < expiredPasses.size(); i++) {
            assertPurchasedPassEquals(expiredPasses.get(i), result.get(i));
        }
    }

    @Test
    void testGetActivePasses() {
        String email = "test@example.com";
        List<PurchasedPass> activePasses = new ArrayList<>();
        activePasses.add(buildPurchasedPass("Pass 1", LocalDate.now().plusDays(1)));
        activePasses.add(buildPurchasedPass("Pass 2", LocalDate.now().plusDays(1)));
        when(purchasedPassRepository.getActivePassesByClient(email)).thenReturn(activePasses);

        List<PurchasedPassResponseDTO> result = purchasedPassService.getActivePasses(email);

        assertEquals(activePasses.size(), result.size());
        for (int i = 0; i < activePasses.size(); i++) {
            assertPurchasedPassEquals(activePasses.get(i), result.get(i));
        }
    }

    @Test
    void testAddPass() {
        String email = "test@example.com";
        String passCategory = "Category 1";
        String passDuration = "Duration 1";
        PassDTO passDTO = new PassDTO(email, passCategory, passDuration);
        Client client = buildClient(email);
        PassCategory passCategoryObj = buildPassCategory(passCategory);
        PurchasedPass purchasedPass = DtoMapper.toEntity(client, passCategoryObj);

        when(clientService.getClientByEmail(email)).thenReturn(client);
        when(passCategoryService.getPassCategoryByNameAndDuration(anyString(), anyString())).thenReturn(passCategoryObj);
        when(purchasedPassRepository.save(purchasedPass)).thenReturn(purchasedPass);

        ResponseEntity<String> response = purchasedPassService.addPass(passDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Purchased successfully", response.getBody());
        verify(purchasedPassRepository).save(any(PurchasedPass.class));
    }

    @Test
    void testDeletePassesByClientEmail() {
        String email = "test@example.com";
        Client client = buildClient(email);
        List<PurchasedPass> purchasedPasses = new ArrayList<>();
        PurchasedPass pass1 = buildPurchasedPass("Pass 1", LocalDate.now());
        pass1.setClient(client);
        PurchasedPass pass2 = buildPurchasedPass("Pass 2", LocalDate.now());
        pass2.setClient(client);
        purchasedPasses.add(pass1);
        purchasedPasses.add(pass2);

        when(purchasedPassRepository.findAllByByClient(email)).thenReturn(purchasedPasses);

        ResponseEntity<String> response = purchasedPassService.deletePassesByClientEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Passes of client deleted successfully", response.getBody());
        verify(purchasedPassRepository).deleteAllByClient_Email(email);
    }

    private PurchasedPass buildPurchasedPass(String category, LocalDate expirationDate) {
        PassCategory passCategory = buildPassCategory(category);
        return PurchasedPass.builder()
                .passCategory(passCategory)
                .expireTime(expirationDate)
                .build();
    }

    private PassCategory buildPassCategory(String category) {
        return PassCategory.builder()
                .category(category)
                .passDuration("Duration 1")
                .passExpireInDay(10L)
                .price(10)
                .build();
    }

    private Client buildClient(String email) {
        return Client.builder()
                .email(email)
                .password("password")
                .purchasedPass(new ArrayList<>())
                .build();
    }

    private void assertPurchasedPassEquals(PurchasedPass purchasedPass, PurchasedPassResponseDTO responseDTO) {
        assertEquals(purchasedPass.getPassCategory().getPassDuration(), responseDTO.passType());
        assertEquals(purchasedPass.getExpireTime(), responseDTO.expirationDate());
        assertEquals(purchasedPass.getPassCategory().getCategory(), responseDTO.category());
        assertEquals(purchasedPass.getPassCategory().getPrice(), responseDTO.price());
    }
}
