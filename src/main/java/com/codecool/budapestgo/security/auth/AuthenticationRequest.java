package com.codecool.budapestgo.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class AuthenticationRequest {
    @Email
    private final String email;
    @NotBlank
    @Size(min = 8)
    private final String password;
}
