package com.codecool.budapestgo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class FrontendController {
    @GetMapping({"/index"})
    public String index() {
        return "index";
    }
}
