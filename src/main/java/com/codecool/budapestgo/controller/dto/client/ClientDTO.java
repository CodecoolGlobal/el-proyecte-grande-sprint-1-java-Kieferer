package com.codecool.budapestgo.controller.dto.client;

import com.codecool.budapestgo.dao.model.Client;
import lombok.Getter;

import java.util.Objects;
public record ClientDTO(Long id, String email, String clientCategoryType) {
    public static ClientDTO of(Client client) {
        return new ClientDTO(client.getId(),client.getEmail(), client.getType().name());
    }
}
