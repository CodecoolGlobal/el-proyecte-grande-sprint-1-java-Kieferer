package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientRegisterDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.repository.ClientRepository;
import com.codecool.budapestgo.dao.types.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
    public ResponseEntity<ClientDTO> getClientById(Long id){
        return clientRepository.findById(id)
                .map(client ->  ResponseEntity.ok(ClientDTO.of(client)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    public void deleteClientById(Long id){
        clientRepository.deleteById(id);
    }
    public ResponseEntity<String> addClient(ClientRegisterDTO clientToRegister){
        Optional<Client> searchedClient = clientRepository.findClientByEmail(clientToRegister.email());
        if(searchedClient.isEmpty()) {
            Client client = Client.builder()
                    .email(clientToRegister.email())
                    .password(clientToRegister.password())
                    .role(Role.CUSTOMER)
                    .build();
            clientRepository.save(client);
            return ResponseEntity.ok("User created");
        }
        return ResponseEntity.badRequest().body("User with that email already exist.");
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

    public Client login(String email, String password) {
        Optional<Client> client = clientRepository.findClientByEmail(email);
        if (client.isPresent()){
            if (client.get().getPassword().equals(password))
                return client.get();
            else
                //TODO: costume exception for bad password
                throw new RuntimeException();
        }
        else
            //TODO: costume exception for bad email
            throw new NoSuchElementException();
    }
}
