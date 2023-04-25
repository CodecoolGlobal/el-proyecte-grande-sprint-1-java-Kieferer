package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientRegisterDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.dao.model.client.Client;
import com.codecool.budapestgo.dao.model.client.ClientRepository;
import com.codecool.budapestgo.data.ClientCategoryType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public List<ClientDTO> getAllClient(){
    return clientRepository.findAll()
                           .stream()
                           .map(ClientDTO::of)
                           .toList();
    }
    public Optional<ClientDTO> getClientById(Integer id){
        return clientRepository.findById(id)
                .map(ClientDTO::of);
    }
    public void deleteClientById(Integer id){
        clientRepository.deleteById(id);
    }
    public void addClient(ClientRegisterDTO clientToRegister){
        Client client = Client.builder()
                .email(clientToRegister.email())
                .password(clientToRegister.password())
                .type(ClientCategoryType.CUSTOMER)
                .build();
        clientRepository.save(client);
    }
    public ResponseEntity<String> updateClient(ClientUpdateDTO updateClient){
        Optional<Client> client = clientRepository.findById(updateClient.id());
        if(client.isPresent()) {
            client.get().setPassword(updateClient.password());
            clientRepository.save(client.get());
            return ResponseEntity.ok("Client updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
    }
}
