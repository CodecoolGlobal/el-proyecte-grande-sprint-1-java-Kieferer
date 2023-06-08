package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryRegisterDTO;
import com.codecool.budapestgo.controller.dto.pass.PassCategoryResponseDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.repository.PassCategoryRepository;
import com.codecool.budapestgo.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        passCategories.add(buildPassCategory(1L, "Category 1", "Duration 1", 3L, 100));
        passCategories.add(buildPassCategory(2L, "Category 2", "Duration 2", 4L, 200));
        when(passCategoryRepository.findAll()).thenReturn(passCategories);

        List<PassCategoryResponseDTO> result = passCategoryService.getAllPassCategory();

        assertEquals(passCategories.size(), result.size());
        for (int i = 0; i < passCategories.size(); i++) {
            assertPassCategoryEquals(passCategories.get(i), result.get(i));
        }
    }

    @Test
    void testAddPassCategory() {
        PassCategoryRegisterDTO registerDTO = new PassCategoryRegisterDTO("Category 1", "Duration 1", 1L, 100);
        PassCategory passCategory = DtoMapper.toEntity(registerDTO);
        when(passCategoryRepository.findByCategoryAndPassDuration(registerDTO.category(), registerDTO.passDuration()))
                .thenReturn(Optional.empty());
        when(passCategoryRepository.save(any(PassCategory.class))).thenReturn(passCategory);

        ResponseEntity<String> response = passCategoryService.addPassCategory(registerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pass category created successfully", response.getBody());
        verify(passCategoryRepository, times(1)).save(any(PassCategory.class));
    }

    @Test
    void testAddPassCategoryWhenCategoryExist() {
        PassCategoryRegisterDTO registerDTO = new PassCategoryRegisterDTO("Category 1", "Duration 1", 1L, 100);
        when(passCategoryRepository.findByCategoryAndPassDuration(registerDTO.category(), registerDTO.passDuration()))
                .thenReturn(Optional.of(buildPassCategory(1L, "Category 1", "Duration 1", 1L, 100)));

        assertThrows(DataIntegrityViolationException.class, () -> passCategoryService.addPassCategory(registerDTO));
        verify(passCategoryRepository, never()).save(any(PassCategory.class));
    }

    @Test
    void testDeletePassCategoryById() {
        Long categoryId = 1L;
        when(passCategoryRepository.findById(categoryId)).thenReturn(
                Optional.of(buildPassCategory(1L, "Category 1", "Duration 1", 1L, 100)));

        ResponseEntity<String> response = passCategoryService.deletePassCategoryById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted successfully", response.getBody());
        verify(passCategoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testDeletePassCategoryByIdThrowsNotFound() {
        Long categoryId = 1L;
        when(passCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> passCategoryService.deletePassCategoryById(categoryId));
        verify(passCategoryRepository, never()).deleteById(categoryId);
    }

    @Test
    void testUpdatePassCategory() {
        PassCategoryResponseDTO responseDTO = new PassCategoryResponseDTO("Category 1", "Duration 1",1L,100,3L);
        PassCategory updatedPassCategory = DtoMapper.toEntity(responseDTO);
        when(passCategoryRepository.findById(responseDTO.id())).thenReturn(
                Optional.of(buildPassCategory(1L, "Category 1", "Duration 1", 1L, 100)));
        when(passCategoryRepository.saveAndFlush(any(PassCategory.class))).thenReturn(updatedPassCategory);

        ResponseEntity<String> response = passCategoryService.updatePassCategory(responseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated successfully", response.getBody());
        verify(passCategoryRepository, times(1)).saveAndFlush(any(PassCategory.class));
    }

    @Test
    void testUpdatePassCategoryNotFound() {
        PassCategoryResponseDTO responseDTO = new PassCategoryResponseDTO("Category 1", "Duration 1",1L,100,3L);
        when(passCategoryRepository.findById(responseDTO.id())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> passCategoryService.updatePassCategory(responseDTO));
        verify(passCategoryRepository, never()).saveAndFlush(any(PassCategory.class));
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
