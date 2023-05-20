package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping
    public ResponseEntity<String> updateClient(@Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {
       return clientService.updateClient(clientUpdateDTO);
    }

}
