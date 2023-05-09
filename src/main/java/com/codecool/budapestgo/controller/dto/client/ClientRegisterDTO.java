package com.codecool.budapestgo.controller.dto.client;

import com.codecool.budapestgo.dao.model.Client;

public record ClientRegisterDTO(String email, String password) {
    public static ClientRegisterDTO of(Client client){
        return new ClientRegisterDTO(client.getEmail(), client.getPassword());
    }
}
