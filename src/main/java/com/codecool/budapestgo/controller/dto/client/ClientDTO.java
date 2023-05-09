package com.codecool.budapestgo.controller.dto.client;

import com.codecool.budapestgo.dao.model.Client;
import lombok.Getter;

import java.util.Objects;
@Getter
public final class ClientDTO {
    private final Integer id;
    private final String email;
    private final String clientCategoryType;

    public ClientDTO(Integer id, String email, String clientCategoryType) {
        this.id = id;
        this.email = email;
        this.clientCategoryType = clientCategoryType;
    }

    public static ClientDTO of(Client client) {
        return new ClientDTO(client.getId(),client.getEmail(), client.getType().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClientDTO) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "ClientDTO[" +
                "id=" + id + ", " +
                "email=" + email + ']';
    }

}
