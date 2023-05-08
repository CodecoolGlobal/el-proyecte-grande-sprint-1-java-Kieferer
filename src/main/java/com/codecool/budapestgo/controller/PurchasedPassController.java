package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PurchasedPassResponseDTO;
import com.codecool.budapestgo.service.PurchasedPassService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<PurchasedPassResponseDTO> getActivePasses(@PathVariable Integer id){
        return purchasedPassService.getActivePasses(id);
    }
    @GetMapping("/expired/{id}")
    public List<PurchasedPassResponseDTO> getExpiredPasses(@PathVariable Integer id){
        return purchasedPassService.getExpiredPasses(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPass(@RequestBody PassDTO passDTO){
       return purchasedPassService.addPass(passDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAllPassOfClient(@PathVariable int id){
       return purchasedPassService.deletePassesByClientId(id);
    }
}
