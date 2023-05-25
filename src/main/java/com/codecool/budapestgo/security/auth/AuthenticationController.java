package com.codecool.budapestgo.security.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {
    private String state;
    private final AuthenticationService service;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
          @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/oauth2/authorizationPageUrl/google")
    public String getAuthorizationPageUrl(){
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("google");
        if (clientRegistration != null) {
            String authorizationUri = clientRegistration.getProviderDetails().getAuthorizationUri();
            String clientId = clientRegistration.getClientId();
            String redirectUri ="http://localhost:8080/login/oauth2/code/";
            String scope = String.join(" ", clientRegistration.getScopes());
            String state = generateState();

            String authorizationUrl = String.format("%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=code&state=%s",
                    authorizationUri, clientId, redirectUri, scope, state);

            return authorizationUrl;
        }


        return null;
    }

    @GetMapping("login/oauth2/code/{registrationId}")
    public void handleOauth2Redirect(@PathVariable String registrationId,@RequestParam String state){
        if (state.equals(this.state)) {
            System.out.println(state);
            System.out.println(registrationId);
        } else {
            System.out.println("Invalid state");
        }
    }

    private String generateState() {
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        this.state = state;
        return state;
    }


}
