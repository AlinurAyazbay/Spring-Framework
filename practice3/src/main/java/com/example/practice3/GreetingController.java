package com.example.practice3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    // Constructor injection — Spring passes in whichever GreetingService bean won,
    // based on the conditional logic in GreetingConfig. No @Autowired needed here;
    // Spring auto-detects a single constructor as the injection point.
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/greet")
    public String greet() {
        return greetingService.getGreeting();
    }
}