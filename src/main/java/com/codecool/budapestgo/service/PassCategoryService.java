package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryRegisterDTO;
import com.codecool.budapestgo.controller.dto.pass.PassCategoryResponseDTO;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.repository.PassCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PassCategoryService {
    private final PassCategoryRepository passCategoryRepository;

    public List<PassCategoryResponseDTO> getAllPassCategory(){
        return passCategoryRepository.findAll()
                .stream()
                .map(PassCategoryResponseDTO::of)
                .toList();
    }
    public ResponseEntity<String> addPassCategory(PassCategoryRegisterDTO passCategoryRegisterDTO){
      try {
          Optional<PassCategory> optionalPassCategory = passCategoryRepository.findByCategoryAndPassDuration(passCategoryRegisterDTO.category(), passCategoryRegisterDTO.passDuration());
          if (optionalPassCategory.isEmpty()) {
              PassCategory passCategory = PassCategory.builder()
                      .category(passCategoryRegisterDTO.category())
                      .passDuration(passCategoryRegisterDTO.passDuration())
                      .passExpireInDay(passCategoryRegisterDTO.passExpireInDay())
                      .price(passCategoryRegisterDTO.price())
                      .build();

              passCategoryRepository.save(passCategory);
              return ResponseEntity.ok("Pass category saved.");
          }
          return ResponseEntity.badRequest().body("Pass category already exist.");
      }catch (DataIntegrityViolationException e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    public ResponseEntity<String> deletePassCategoryById(Long id){
        if (id == 0) {
            return ResponseEntity.badRequest().body("ID cannot be 0.");
        }
        try {
            passCategoryRepository.deleteById(id);
            return ResponseEntity.ok("Pass category deleted.");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<String> updatePassCategory(PassCategoryResponseDTO passCategoryResponseDTO){
        try {
            Optional<PassCategory> optionalPassCategory = passCategoryRepository.findById(passCategoryResponseDTO.id());
            if (optionalPassCategory.isPresent()) {
                PassCategory updatedPass = PassCategory.builder()
                        .passDuration(passCategoryResponseDTO.passDuration())
                        .passExpireInDay(passCategoryResponseDTO.passExpireInDay())
                        .category(passCategoryResponseDTO.category())
                        .price(passCategoryResponseDTO.price())
                        .build();
                passCategoryRepository.saveAndFlush(updatedPass);
                return ResponseEntity.ok("Pass category updated");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pass category not found");
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
