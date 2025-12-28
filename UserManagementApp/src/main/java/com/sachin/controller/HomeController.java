package com.sachin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") // This handles the root of your application
public class HomeController {

    @GetMapping
    public String home() {
        return "index";
    }
}