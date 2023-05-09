package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryRegisterDTO;
import com.codecool.budapestgo.controller.dto.pass.PassCategoryResponseDTO;
import com.codecool.budapestgo.controller.dto.validator.DTOValidator;
import com.codecool.budapestgo.service.PassCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class PassCategoryController {
    private final PassCategoryService passCategoryService;
    @GetMapping("/all")
    public List<PassCategoryResponseDTO> getAllCategory(){
        return passCategoryService.getAllPassCategory();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPass(@RequestBody PassCategoryRegisterDTO passCategoryRegisterDTO){
       try {
           if (DTOValidator.registrationIsInvalid(passCategoryRegisterDTO)) {
               return ResponseEntity.badRequest().body("Fields cannot be empty");
           } else {
               return passCategoryService.addPassCategory(passCategoryRegisterDTO);
           }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassCategoryById(@PathVariable Long id){
       return passCategoryService.deletePassCategoryById(id);
    }
    @PutMapping
    public ResponseEntity<String> updatePassCategory(@RequestBody PassCategoryResponseDTO passCategoryResponseDTO){
        return passCategoryService.updatePassCategory(passCategoryResponseDTO);
    }
}
