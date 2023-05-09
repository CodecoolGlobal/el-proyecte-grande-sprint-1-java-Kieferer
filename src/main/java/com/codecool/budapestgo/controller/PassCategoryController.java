package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryDTO;
import com.codecool.budapestgo.service.PassCategoryService;
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
    public List<PassCategoryDTO> getAllCategory(){
        return passCategoryService.getAllPassCategory();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPass(@RequestBody PassCategoryDTO passCategoryDTO){
       return passCategoryService.addPassCategory(passCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassCategoryById(@PathVariable int id){
       return passCategoryService.deletePassCategoryById(id);
    }
    @PutMapping
    public ResponseEntity<String> updatePassCategory(@RequestBody PassCategoryDTO passCategoryDTO){
        return passCategoryService.updatePassCategory(passCategoryDTO);
    }
}
