package com.example.practice3;

public class FriendlyGreetingService implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hey there! Great to see you — this is the friendly greeting service!";
    }
}