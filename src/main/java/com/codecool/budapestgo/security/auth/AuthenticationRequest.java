package com.codecool.budapestgo.security.auth;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class AuthenticationRequest {

    private final String email;
    private final String password;
}
