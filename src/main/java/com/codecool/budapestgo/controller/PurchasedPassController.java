package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PurchasedPassResponseDTO;
import com.codecool.budapestgo.controller.dto.validator.DTOValidator;
import com.codecool.budapestgo.service.PurchasedPassService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/pass")
@AllArgsConstructor
public class PurchasedPassController {
    private final PurchasedPassService purchasedPassService;

    @GetMapping("/all")
    public List<PurchasedPassResponseDTO> getAllPass(){
        return purchasedPassService.getAllPass();
    }

    @GetMapping("/active/{id}")
    public List<PurchasedPassResponseDTO> getActivePasses(@PathVariable Long id){
        return purchasedPassService.getActivePasses(id);
    }
    @GetMapping("/expired/{id}")
    public List<PurchasedPassResponseDTO> getExpiredPasses(@PathVariable Long id){
        return purchasedPassService.getExpiredPasses(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPass(@RequestBody PassDTO passDTO){
        try {
            if (DTOValidator.registrationIsInvalid(passDTO)) {
                return ResponseEntity.badRequest().body("Fields cannot be empty");
            } else {
                return purchasedPassService.addPass(passDTO);
            }
        }catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAllPassOfClient(@PathVariable Long id){
       return purchasedPassService.deletePassesByClientId(id);
    }
}
