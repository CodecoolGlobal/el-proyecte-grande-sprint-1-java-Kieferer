package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    @GetMapping("/all")
    public List<ClientDTO> getAllClient() {
        return clientService.getAllClient();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteClientById(@Valid @PathVariable @NotBlank String email) {
       return clientService.deleteClientByEmail(email);
    }

    @PutMapping
    public ResponseEntity<String> updateClient(@Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {
       return clientService.updateClient(clientUpdateDTO);
    }

}
