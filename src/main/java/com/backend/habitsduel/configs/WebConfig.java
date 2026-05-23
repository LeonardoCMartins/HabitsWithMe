package com.backend.habitsduel.configs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebConfig {
    @GetMapping("/")
    public String index() {
        return "redirect:/habitbattle.html";
    }
}