package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientRegisterDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.service.ClientService;
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
    public List<ClientDTO> getAllClient(){
        return clientService.getAllClient();
    }
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable int id){
        clientService.deleteClientById(id);
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientRegisterDTO clientRegisterDTO){
       return clientService.addClient(clientRegisterDTO);
    }
    @PutMapping()
    public ResponseEntity<String> updateClient(@RequestBody ClientUpdateDTO clientUpdateDTO){
        return clientService.updateClient(clientUpdateDTO);
    }
}
