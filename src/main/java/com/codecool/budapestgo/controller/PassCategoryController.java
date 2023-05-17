package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryRegisterDTO;
import com.codecool.budapestgo.controller.dto.pass.PassCategoryResponseDTO;
import com.codecool.budapestgo.service.PassCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/register")
    public ResponseEntity<String> registerPass(@Valid @RequestBody PassCategoryRegisterDTO passCategoryRegisterDTO){
       return passCategoryService.addPassCategory(passCategoryRegisterDTO);
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<String> deletePassCategoryById(@Valid @PathVariable @Min(1) Long id){
       return passCategoryService.deletePassCategoryById(id);
    }
    @PutMapping("/api")
    public ResponseEntity<String> updatePassCategory(@Valid @RequestBody PassCategoryResponseDTO passCategoryResponseDTO){
       return passCategoryService.updatePassCategory(passCategoryResponseDTO);
    }
}
