package com.codecool.budapestgo.controller.dto.client;

import com.codecool.budapestgo.dao.model.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientDTO(@NotNull @Min(1) Long id, @Email String email, @NotNull @NotBlank String clientCategoryType) {
    public static ClientDTO of(Client client) {
        return new ClientDTO(client.getId(),client.getEmail(), client.getType().name());
    }
}
