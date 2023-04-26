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
    @GetMapping("/active")
    public List<PassResponseDTO> getActivePasses(@RequestBody Integer id){
        return passService.getActivePasses(id);
    }
    @GetMapping("/expired")
    public List<PassResponseDTO> getExpiredPasses(@RequestBody Integer id){
        return passService.getExpiredPasses(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPass(@RequestBody PassDTO passDTO){
       return passService.addPass(passDTO);
    }


}
