package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.ClientDTO;
import com.codecool.budapestgo.dao.client.Client;
import com.codecool.budapestgo.dao.client.ClientRepository;
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
    return clientRepository.findAll().stream().map(ClientDTO::of).toList();
    }
    public Optional<ClientDTO> getClientById(Integer id){
    return clientRepository.findById(id).map(ClientDTO::of);
    }
    public void deleteClientById(Integer id){
        clientRepository.deleteById(id);
    }
    public void addClient(Client client){
        clientRepository.save(client);
    }

}
