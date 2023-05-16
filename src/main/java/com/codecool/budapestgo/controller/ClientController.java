package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientLoginDTO;
import com.codecool.budapestgo.controller.dto.client.ClientRegisterDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.service.ClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public List<ClientDTO> getAllClient() {
        return clientService.getAllClient();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientById(@Valid @PathVariable @Min(1) Long id) {
       return clientService.deleteClientById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@Valid @RequestBody ClientRegisterDTO clientRegisterDTO){
       return clientService.addClient(clientRegisterDTO);
    }

    @PutMapping()
    public ResponseEntity<String> updateClient(@Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {
       return clientService.updateClient(clientUpdateDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ClientLoginDTO loginRequest, HttpServletResponse response) {
        ResponseEntity<Client> client = clientService.login(loginRequest.email(),loginRequest.password());
        if(client.hasBody()) {
            Cookie privilageCookie = new Cookie("privilege", client.getBody().getRole().toString());
            Cookie emailCookie = new Cookie("email", client.getBody().getEmail());
            Cookie idCookie = new Cookie("id", client.getBody().getId().toString());
            setupCookie(idCookie);
            setupCookie(privilageCookie);
            setupCookie(emailCookie);
            response.addCookie(idCookie);
            response.addCookie(privilageCookie);
            response.addCookie(emailCookie);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.badRequest().body("Password or email is incorrect.");
    }

    private void setupCookie(Cookie cookie) {
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 30); //1 day
    }
}
