package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientRegisterDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.repository.ClientRepository;
import com.codecool.budapestgo.dao.types.ClientCategoryType;
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
    public ResponseEntity<String> deleteClientById(Long id){
        if(isClientExistById(id)) {
            clientRepository.deleteById(id);
            return ResponseEntity.ok("Client deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid id");
    }
    public ResponseEntity<String> addClient(ClientRegisterDTO clientRegisterDTO){
                if (getClientByEmail(clientRegisterDTO.email()).isEmpty()) {
                    Client client = customerOf(clientRegisterDTO);
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
        Optional<Client> client = getClientByEmail(email);
        if (client.isPresent()){
            if (client.get().getPassword().equals(password))
                return client.get();
            else
                throw new RuntimeException();
        }
        else
            //TODO: costume exception for bad email
            throw new NoSuchElementException();
    }

    private Client customerOf(ClientRegisterDTO clientRegisterDTO){
        return Client.builder()
                .email(clientRegisterDTO.email())
                .password(clientRegisterDTO.password())
                .type(ClientCategoryType.CUSTOMER)
                .build();
    }
    private Optional<Client> getClientByEmail(String email){
        return clientRepository.findClientByEmail(email);
    }
    private boolean isClientExistById(Long id){
        return clientRepository.findById(id).isPresent();
    }
}
