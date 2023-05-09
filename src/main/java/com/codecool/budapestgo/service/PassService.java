package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PassResponseDTO;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.repository.ClientRepository;
import com.codecool.budapestgo.dao.model.Pass;
import com.codecool.budapestgo.dao.repository.PassRepository;
import com.codecool.budapestgo.dao.types.PassType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PassService {
    private final PassRepository passRepository;
    private final ClientRepository clientRepository;
    public PassService(PassRepository passRepository, ClientRepository clientRepository) {
        this.passRepository = passRepository;
        this.clientRepository = clientRepository;
    }
    public List<PassResponseDTO> getAllPass(){
        return passRepository.findAll().stream().map(PassResponseDTO::of) .toList();
    }

    public ResponseEntity<String> addPass(PassDTO passDTO){
        Optional<Client> client = clientRepository.findById(passDTO.clientId());
        if(client.isPresent()) {
            PassType type = PassType.valueOf(passDTO.passType());
            LocalDate date = LocalDate.now();
            Pass pass = Pass.builder()
                    .client(client.get())
                    .passType(type)
                    .startTime(date)
                    .expireTime(Pass.calculateExpireTime(date,type))
                    .build();

            passRepository.save(pass);
            return ResponseEntity.ok("Pass have been purchased");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Client not found.");
    }
    public List<PassResponseDTO> getExpiredPasses(Integer id){
        return passRepository.getAllExpiredPassesByClient_id(id)
                .stream()
                .map(PassResponseDTO::of)
                .toList();
    }
    public List<PassResponseDTO> getActivePasses(Integer id){
        return passRepository.getActivePassesByClient_id(id)
                .stream()
                .map(PassResponseDTO::of)
                .toList();
    }
}
