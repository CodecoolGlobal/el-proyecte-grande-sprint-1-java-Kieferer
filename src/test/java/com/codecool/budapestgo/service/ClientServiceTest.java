package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.client.ClientDTO;
import com.codecool.budapestgo.controller.dto.client.ClientUpdateDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.repository.ClientRepository;
import com.codecool.budapestgo.dao.types.Role;
import com.codecool.budapestgo.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void testGetAllClientReturnsAllClientsWhenClientsExist() {
        List<Client> clients = new ArrayList<>();
        Client clientOne = buildClient(1L, "client1@example.com", "password1", Role.CUSTOMER);
        Client clientTwo = buildClient(2L, "client2@example.com", "password2", Role.ADMIN);
        clients.add(clientOne);
        clients.add(clientTwo);
        when(clientRepository.findAll()).thenReturn(clients);

        List<ClientDTO> clientDTOs = clientService.getAllClient();

        assertEquals(clients.size(), clientDTOs.size());

        for (int i = 0; i < clients.size(); i++) {
            assertClientEquals(clients.get(i), clientDTOs.get(i));

        }
    }

    private Client buildClient(Long id, String email, String password,Role role) {
        return Client.builder()
                .id(id)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    private void assertClientEquals(Client client, ClientDTO clientDTO) {
        assertEquals(client.getId(),clientDTO.id());
        assertEquals(client.getEmail(), clientDTO.email());
        assertEquals(client.getRole().name(), clientDTO.clientCategoryType());
    }
}
