package com.example.practice2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    private final AppProperties appProperties;

    public ConfigController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping("/config")
    public String showConfig() {
        return "Name: " + appProperties.getName() + " | Description: " + appProperties.getDescription();
    }
}