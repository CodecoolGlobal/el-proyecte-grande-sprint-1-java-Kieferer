package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.service.PassService;
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
    public List<PassDTO> getAllPass(){
        return passService.getAllPass();
    }

    @PostMapping("/register")
    public void registerPass(@RequestBody PassDTO passDTO){
        passService.addPass(passDTO);
    }


}
