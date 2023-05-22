package com.codecool.budapestgo.customExceptionHandler;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message + " not found.");
    }
}
