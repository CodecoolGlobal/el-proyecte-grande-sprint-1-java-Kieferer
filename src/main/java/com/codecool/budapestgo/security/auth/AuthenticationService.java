package com.codecool.budapestgo.security.auth;

import com.codecool.budapestgo.customExceptionHandler.InvalidLoginException;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.repository.ClientRepository;
import com.codecool.budapestgo.security.config.JwtService;
import com.codecool.budapestgo.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        String password = passwordEncoder.encode(request.getPassword());
        Client client = DtoMapper.toEntity(request,password);
        clientRepository.save(client);

        String jwtToken = jwtService.generateToken(client);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Client client = clientRepository.findClientByEmail(request.getEmail())
                .orElseThrow(InvalidLoginException::new);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        HashMap<String,Object> additionalClaims  = new HashMap<>();
        additionalClaims.put("role",client.getRole());
        String jwtToken = jwtService.generateToken(additionalClaims,client);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        }catch (Exception e){
            throw new InvalidLoginException();
        }
    }
}
