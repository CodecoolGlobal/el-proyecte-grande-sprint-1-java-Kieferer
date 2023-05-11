package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryRegisterDTO;
import com.codecool.budapestgo.controller.dto.pass.PassCategoryResponseDTO;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.repository.PassCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
          if (isCategoryNotExist(passCategoryRegisterDTO)) {
              PassCategory passCategory = passCategoryOf(passCategoryRegisterDTO);
              passCategoryRepository.save(passCategory);
              return ResponseEntity.ok("Pass category saved.");
          }
          return ResponseEntity.badRequest().body("Pass category already exist.");
    }
    public ResponseEntity<String> deletePassCategoryById(Long id){
        if(isCategoryExistById(id)) {
            passCategoryRepository.deleteById(id);
            return ResponseEntity.ok("Pass category deleted.");
        }
            return ResponseEntity.notFound().build();
    }
    public ResponseEntity<String> updatePassCategory(PassCategoryResponseDTO passCategoryResponseDTO){
            if (isCategoryExistById(passCategoryResponseDTO.id())) {
                PassCategory updatedPass = passCategoryOf(passCategoryResponseDTO);
                passCategoryRepository.saveAndFlush(updatedPass);
                return ResponseEntity.ok("Pass category updated");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pass category not found");
    }
    private PassCategory passCategoryOf(PassCategoryRegisterDTO passCategoryRegisterDTO){
        return PassCategory.builder()
                .passDuration(passCategoryRegisterDTO.passDuration())
                .passExpireInDay(passCategoryRegisterDTO.passExpireInDay())
                .category(passCategoryRegisterDTO.category())
                .price(passCategoryRegisterDTO.price())
                .build();
    }
    private PassCategory passCategoryOf(PassCategoryResponseDTO passCategoryResponseDTO){
        return PassCategory.builder()
                .id(passCategoryResponseDTO.id())
                .passDuration(passCategoryResponseDTO.passDuration())
                .passExpireInDay(passCategoryResponseDTO.passExpireInDay())
                .category(passCategoryResponseDTO.category())
                .price(passCategoryResponseDTO.price())
                .build();
    }
    private boolean isCategoryExistById(Long id){
        return passCategoryRepository.findById(id).isPresent();
    }
    private boolean isCategoryNotExist(PassCategoryRegisterDTO passCategoryRegisterDTO){
        return passCategoryRepository.findByCategoryAndPassDuration(passCategoryRegisterDTO.category(), passCategoryRegisterDTO.passDuration()).isEmpty();
    }
}
