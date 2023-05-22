package com.codecool.budapestgo.security.auth;

import com.codecool.budapestgo.dao.types.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
    private Role role;
}
