package com.codecool.budapestgo.utils;

import org.springframework.http.ResponseEntity;

public class Response {
    public static ResponseEntity<String> successful(String message){
        return ResponseEntity.ok(message + " successfully");
    }
    public static ResponseEntity<String> created(String message){
        return ResponseEntity.ok(message + " created successfully");
    }

}
