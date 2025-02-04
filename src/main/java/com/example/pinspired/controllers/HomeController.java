package com.example.pinspired.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }



    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }
}
