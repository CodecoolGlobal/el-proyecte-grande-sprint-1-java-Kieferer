package com.codecool.budapestgo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    @GetMapping({"/", "/index.html"})
    public String index() {
        return "index.html";
    }
}
