package com.codecool.budapestgo.service;

import com.codecool.budapestgo.dao.model.pass.PassRepository;
import org.springframework.stereotype.Service;

@Service
public class PassService {
    private final PassRepository passRepository;

    public PassService(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

}
