package com.codecool.budapestgo.controller.dto.client;

import com.codecool.budapestgo.dao.model.Client;

public record ClientUpdateDTO (Long id, String password){
    public static ClientUpdateDTO of(Client client){
        return new ClientUpdateDTO(client.getId(), client.getPassword());
    }
}
