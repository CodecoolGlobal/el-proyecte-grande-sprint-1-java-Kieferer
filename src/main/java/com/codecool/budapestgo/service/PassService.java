package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.dao.model.pass.Pass;
import com.codecool.budapestgo.dao.model.pass.PassRepository;
import com.codecool.budapestgo.dao.model.pass.PassType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PassService {
    private final PassRepository passRepository;

    public PassService(PassRepository passRepository) {
        this.passRepository = passRepository;
    }
    public List<PassDTO> getAllPass(){
        return passRepository.findAll().stream().map(PassDTO::of) .toList();
    }

    public void addPass(PassDTO passDTO){
        PassType type = PassType.valueOf(passDTO.getPassType());

        Pass pass = Pass.builder()
                        .client_id(passDTO.getClientId())
                        .passType(type)
                        .startTime(LocalDate.now())
                        .expireTime(Pass.calculateExpireTime(LocalDate.now(),type))
                        .build();

        passRepository.save(pass);
    }
    public Optional<PassDTO> getClientById(Integer id){
        return passRepository.findById(id)
                .map(PassDTO::of);
    }
}
