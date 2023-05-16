package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientRegisterDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.repository.ClientRepository;
import com.codecool.budapestgo.dao.repository.PurchasedPassRepository;
import com.codecool.budapestgo.utils.DtoMapper;
import com.codecool.budapestgo.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PurchasedPassRepository passRepository;
    public List<ClientDTO> getAllClient(){
        return clientRepository.findAll()
                .stream()
                .map(ClientDTO::of)
                .toList();
    }
    public ResponseEntity<String> deleteClientById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client with id " + id));
        passRepository.deleteAllByClient_Id(client.getId());
        clientRepository.deleteById(id);
        return Response.successful("Deleted");
    }
    public ResponseEntity<String> addClient(ClientRegisterDTO clientRegisterDTO){
        Client client = DtoMapper.toEntity(clientRegisterDTO);
        clientRepository.save(client);
        return Response.created("Client");
    }
    public ResponseEntity<String> updateClient(ClientUpdateDTO updateClient){
        Client client = clientRepository.findById(updateClient.id()).orElseThrow(() -> new NotFoundException("Client with id " + updateClient.id()));
        client.setPassword(updateClient.password());
        clientRepository.save(client);
        return Response.successful("Updated");
    }

    public ResponseEntity<Client> login(String email, String password) {
        Client client = getClientByEmail(email);
        System.out.println("asdasd");
            if (client.getPassword().equals(password)) {
                System.out.println("asdasd");
                return ResponseEntity.ok(client);
            }
            else
                return ResponseEntity.badRequest().build();
    }

    private Client getClientByEmail(String email){
        return clientRepository.findClientByEmail(email).orElseThrow(() -> new RuntimeException("Wrong username or password"));
    }
}
