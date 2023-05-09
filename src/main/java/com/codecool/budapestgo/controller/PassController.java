package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PassResponseDTO;
import com.codecool.budapestgo.service.PassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pass")
public class PassController {
    private final PassService passService;

    public PassController(PassService passService) {
        this.passService = passService;
    }
    @GetMapping("/all")
    public List<PassResponseDTO> getAllPass(){
        return passService.getAllPass();
    }
    @GetMapping("/active/{id}")
    public List<PassResponseDTO> getActivePasses(@PathVariable Integer id){
        return passService.getActivePasses(id);
    }
    @GetMapping("/expired/{id}")
    public List<PassResponseDTO> getExpiredPasses(@PathVariable Integer id){
        return passService.getExpiredPasses(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPass(@RequestBody PassDTO passDTO){
       return passService.addPass(passDTO);
    }


}
