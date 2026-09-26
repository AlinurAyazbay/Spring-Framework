package com.example.practice3;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingConfig {

    @Bean
    @ConditionalOnProperty(prefix = "app.greeting", name = "style", havingValue = "friendly")
    public GreetingService friendlyGreetingService() {
        return new FriendlyGreetingService();
    }

    @Bean
    @ConditionalOnMissingBean(GreetingService.class)
    public GreetingService defaultGreetingService() {
        return new DefaultGreetingService();
    }
}