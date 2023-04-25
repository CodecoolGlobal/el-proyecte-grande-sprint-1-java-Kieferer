package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.ClientDTO;
import com.codecool.budapestgo.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client/")
public class ClientController {
    private ClientService clientService;

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

}
