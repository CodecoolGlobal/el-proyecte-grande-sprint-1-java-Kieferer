package com.codecool.budapestgo.customExceptionHandler;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException() {
        super("Wrong username or password");
    }
}
