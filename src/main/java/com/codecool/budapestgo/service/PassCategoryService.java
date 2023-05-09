package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryDTO;
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

    public List<PassCategoryDTO> getAllPassCategory(){
        return passCategoryRepository.findAll()
                .stream()
                .map(PassCategoryDTO::of)
                .toList();
    }
    public ResponseEntity<String> addPassCategory(PassCategoryDTO passCategoryDTO){
      try {
          Optional<PassCategory> optionalPassCategory = passCategoryRepository.findByCategoryAndPassDuration(passCategoryDTO.getCategory(), passCategoryDTO.getPassDuration());
          if (optionalPassCategory.isEmpty()) {
              PassCategory passCategory = PassCategory.builder()
                      .category(passCategoryDTO.getCategory())
                      .passDuration(passCategoryDTO.getPassDuration())
                      .passExpireInDay(passCategoryDTO.getPassExpireInDay())
                      .price(passCategoryDTO.getPrice())
                      .build();

              passCategoryRepository.save(passCategory);
              return ResponseEntity.ok("Pass category saved.");
          }
          return ResponseEntity.badRequest().body("Pass category already exist.");
      }catch (DataIntegrityViolationException e) {
          return ResponseEntity.badRequest().body("Pass category already exist.");
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
    public ResponseEntity<String> updatePassCategory(PassCategoryDTO passCategoryDTO){
        try {
            Optional<PassCategory> optionalPassCategory = passCategoryRepository.findById(passCategoryDTO.getId());
            if (optionalPassCategory.isPresent()) {
                PassCategory updatedPass = PassCategory.builder()
                        .passDuration(passCategoryDTO.getPassDuration())
                        .passExpireInDay(passCategoryDTO.getPassExpireInDay())
                        .category(passCategoryDTO.getCategory())
                        .price(passCategoryDTO.getPrice())
                        .build();
                passCategoryRepository.saveAndFlush(updatedPass);
                return ResponseEntity.ok("Pass category updated");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pass category not found");
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pass category not found.");
        }
    }
}
