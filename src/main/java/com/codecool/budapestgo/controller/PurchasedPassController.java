package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PurchasedPassResponseDTO;
import com.codecool.budapestgo.service.PurchasedPassService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pass")
@RequiredArgsConstructor
public class PurchasedPassController {
    private final PurchasedPassService purchasedPassService;
    @GetMapping("/all")
    public List<PurchasedPassResponseDTO> getAllPass(){
        return purchasedPassService.getAllPass();
    }

    @GetMapping("/active/{email}")
    public List<PurchasedPassResponseDTO> getActivePasses(@PathVariable String email){
        return purchasedPassService.getActivePasses(email);
    }
    @GetMapping("/expired/{email}")
    public List<PurchasedPassResponseDTO> getExpiredPasses(@PathVariable String email){
        return purchasedPassService.getExpiredPasses(email);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPass(@Valid @RequestBody PassDTO passDTO){
       return purchasedPassService.addPass(passDTO);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteAllPassOfClient(@Valid @PathVariable @NotBlank String email){
      return purchasedPassService.deletePassesByClientEmail(email);
    }
}
