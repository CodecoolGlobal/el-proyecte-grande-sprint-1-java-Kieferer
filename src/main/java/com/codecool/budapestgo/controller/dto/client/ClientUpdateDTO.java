package com.codecool.budapestgo.controller.dto.client;

import com.codecool.budapestgo.dao.model.Client;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClientUpdateDTO (@NotNull @Min(1) Long id,@NotBlank @Size(min = 8, max = 255)String password){
    public static ClientUpdateDTO of(Client client){
        return new ClientUpdateDTO(client.getId(), client.getPassword());
    }
}
