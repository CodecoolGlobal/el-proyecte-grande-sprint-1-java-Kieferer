package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientRegisterDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.dao.model.client.Client;
import com.codecool.budapestgo.service.ClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void deleteClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientRegisterDTO clientRegisterDTO) {
        return clientService.addClient(clientRegisterDTO);
    }

    @PutMapping()
    public ResponseEntity<String> updateClient(@RequestBody ClientUpdateDTO clientUpdateDTO) {
        return clientService.updateClient(clientUpdateDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Client client = clientService.login(email, password);

        Cookie privilageCookie = new Cookie("privilege", client.getType().toString());
        Cookie emailCookie = new Cookie("email", client.getEmail());
        Cookie idCookie = new Cookie("id", client.getId().toString());
        setupCookie(idCookie);
        setupCookie(privilageCookie);
        setupCookie(emailCookie);
        response.addCookie(idCookie);
        response.addCookie(privilageCookie);
        response.addCookie(emailCookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private void setupCookie(Cookie cookie) {
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); //1 day
    }
}
