package com.codecool.budapestgo.controller.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientLoginDTO(@Email String email, @NotBlank @Size(min = 8, max = 255) String password) {
}
