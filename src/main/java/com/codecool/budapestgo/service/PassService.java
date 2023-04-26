package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PassResponseDTO;
import com.codecool.budapestgo.dao.model.pass.Pass;
import com.codecool.budapestgo.dao.model.pass.PassRepository;
import com.codecool.budapestgo.dao.model.pass.PassType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PassService {
    private final PassRepository passRepository;

    public PassService(PassRepository passRepository) {
        this.passRepository = passRepository;
    }
    public List<PassResponseDTO> getAllPass(){
        return passRepository.findAll().stream().map(PassResponseDTO::of) .toList();
    }

    public ResponseEntity<String> addPass(PassDTO passDTO){
        PassType type = PassType.valueOf(passDTO.passType());

        Pass pass = Pass.builder()
                        .client_id(passDTO.clientId())
                        .passType(type)
                        .startTime(LocalDate.now())
                        .expireTime(Pass.calculateExpireTime(LocalDate.now(),type))
                        .build();

        passRepository.save(pass);
        return ResponseEntity.ok("Pass have been purchased");
    }
    public List<PassResponseDTO> getExpiredPasses(Integer id){
        return passRepository.getAllExpiredPassesByClient_id(id,LocalDate.now())
                .stream()
                .map(PassResponseDTO::of)
                .toList();
    }
    public List<PassResponseDTO> getActivePasses(Integer id){
        return passRepository.getActivePassesByClient_id(id,LocalDate.now())
                .stream()
                .map(PassResponseDTO::of)
                .toList();
    }
}
