package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryRegisterDTO;
import com.codecool.budapestgo.controller.dto.pass.PassCategoryResponseDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.repository.PassCategoryRepository;
import com.codecool.budapestgo.utils.DtoMapper;
import com.codecool.budapestgo.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassCategoryService {
    private final PassCategoryRepository passCategoryRepository;

    public List<PassCategoryResponseDTO> getAllPassCategory(){
        return passCategoryRepository.findAll()
                .stream()
                .map(PassCategoryResponseDTO::of)
                .toList();
    }
    public ResponseEntity<String> addPassCategory(PassCategoryRegisterDTO passCategoryRegisterDTO){
        validateCategoryNotExist(passCategoryRegisterDTO);
        PassCategory passCategory = DtoMapper.toEntity(passCategoryRegisterDTO);
        passCategoryRepository.save(passCategory);

        return Response.created("Pass category");
    }
    public ResponseEntity<String> deletePassCategoryById(Long id){
        checkCategoryExistenceById(id);
        passCategoryRepository.deleteById(id);

        return Response.successful("Deleted");
    }
    public ResponseEntity<String> updatePassCategory(PassCategoryResponseDTO passCategoryResponseDTO){
        checkCategoryExistenceById(passCategoryResponseDTO.id());
        PassCategory updatedPass = DtoMapper.toEntity(passCategoryResponseDTO);
        passCategoryRepository.saveAndFlush(updatedPass);

        return Response.successful("Updated");
    }
    public PassCategory getPassCategoryByNameAndDuration(String passCategory, String passDuration){
        return  passCategoryRepository.findByCategoryAndPassDuration(passCategory, passDuration)
                .orElseThrow(() -> new NotFoundException("Pass category "));
    }
    private void checkCategoryExistenceById(Long id){
        passCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Pass category with id " + id));
    }
    private void validateCategoryNotExist(PassCategoryRegisterDTO passCategoryRegisterDTO) {
        if (passCategoryRepository.findByCategoryAndPassDuration(passCategoryRegisterDTO.category(), passCategoryRegisterDTO.passDuration()).isPresent()) {
            throw new DataIntegrityViolationException("Category already exist");
        }
    }
}
