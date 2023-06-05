package com.codecool.budapestgo.security.auth;

public class TokenResponse {
    private String access_token;
    private int expires_in;
    private String id_token;
    private String scope;
    private String token_type;
    private String refresh_token;

    // Include getters and setters for the fields

    public String getAccessToken() {
        return access_token;
    }

    public String getIdToken() {
        return id_token;
    }
}
