package com.codecool.budapestgo.controller.dto.client;

import com.codecool.budapestgo.dao.model.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRegisterDTO(@Email String email, @NotBlank @Size(min = 8, max = 255)String password) {
    public static ClientRegisterDTO of(Client client){
        return new ClientRegisterDTO(client.getEmail(), client.getPassword());
    }
}
