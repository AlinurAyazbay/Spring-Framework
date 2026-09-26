package com.example.practice3;

public class DefaultGreetingService implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hello. This is the default greeting service.";
    }
}