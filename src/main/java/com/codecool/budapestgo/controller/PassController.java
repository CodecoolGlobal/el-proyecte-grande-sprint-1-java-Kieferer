package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.service.PassService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pass")
public class PassController {
    private final PassService passService;

    public PassController(PassService passService) {
        this.passService = passService;
    }

}
