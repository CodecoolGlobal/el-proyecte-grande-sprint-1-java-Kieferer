package com.codecool.budapestgo.security.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
            String redirectUri ="http://localhost:8080/login/oauth2/code";
            String scope = String.join(" ", clientRegistration.getScopes());
            String state = generateState();

            String authorizationUrl = String.format("%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=code&state=%s",
                    authorizationUri, clientId, redirectUri, scope, state);

            return authorizationUrl;
        }


        return null;
    }

    @GetMapping("login/oauth2/code")
    public void handleOauth2Redirect(@RequestParam String state,@RequestParam String code) {
        if (state.equals(this.state)) {

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("code", code);
            requestBody.add("client_id", "65474723555-d7cch1n8uu1c397gkipr0v93b7neeikg.apps.googleusercontent.com");
            requestBody.add("client_secret", "gGOCSPX-E4prNi6gEnwCF-b374iCmT7ovTgz");
            requestBody.add("redirect_uri", "https://localhost:8080/login/oauth2/code");
            requestBody.add("grant_type", "authorization_code");

            // Set the headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Create the HTTP entity with the headers and body
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Make the POST request to the token endpoint
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(
                    "https://www.googleapis.com/oauth2/v4/token",
                    HttpMethod.POST,
                    requestEntity,
                    TokenResponse.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                TokenResponse tokenResponse = responseEntity.getBody();

                // Access the returned token and other fields from tokenResponse
                String accessToken = tokenResponse.getAccessToken();
                String idToken = tokenResponse.getIdToken();
                System.out.println("---------------------------------------------");
                System.out.println("access token: "+accessToken);
                System.out.println(idToken);
                System.out.println("---------------------------------------------");
                // Perform further actions with the obtained tokens
                // ...

            } else {
                System.out.println("Token exchange failed. Status code: " + responseEntity.getStatusCode());
            }
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
