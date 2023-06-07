package com.codecool.budapestgo.controller.dto.client;

import com.codecool.budapestgo.dao.model.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClientUpdateDTO (@NotNull @NotBlank String email,@NotBlank @Size(min = 8, max = 255)String password){
    public static ClientUpdateDTO of(Client client){
        return new ClientUpdateDTO(client.getEmail(), client.getPassword());
    }
}
