package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PurchasedPassResponseDTO;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.model.PurchasedPass;
import com.codecool.budapestgo.dao.repository.PurchasedPassRepository;
import com.codecool.budapestgo.utils.DtoMapper;
import com.codecool.budapestgo.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchasedPassService {
    private final PurchasedPassRepository purchasedPassRepository;
    private final PassCategoryService passCategoryService;
    private final ClientService clientService;

    public List<PurchasedPassResponseDTO> getAllPass(){
        return purchasedPassRepository.findAll()
                .stream()
                .map(PurchasedPassResponseDTO::of)
                .toList();
    }
    public List<PurchasedPassResponseDTO> getExpiredPasses(Long id){
        return purchasedPassRepository.getAllExpiredPassesByClient_id(id)
                .stream()
                .map(PurchasedPassResponseDTO::of)
                .toList();
    }
    public List<PurchasedPassResponseDTO> getActivePasses(Long id){
        return purchasedPassRepository.getActivePassesByClient_id(id)
                .stream()
                .map(PurchasedPassResponseDTO::of)
                .toList();
    }
    public ResponseEntity<String> addPass(PassDTO passDTO){
        Client client = clientService.getClientById(passDTO.clientId());
        PassCategory pass = passCategoryService.getPassCategoryByNameAndDuration(passDTO.passCategory(), passDTO.passDuration());
        PurchasedPass purchasedPass = DtoMapper.toEntity(client, pass);

        purchasedPassRepository.save(purchasedPass);
        return Response.successful("Purchased");
    }
    public ResponseEntity<String> deletePassesByClientId(Long id){
        clientService.getClientById(id);
        purchasedPassRepository.deleteAllByClient_Id(id);
        return Response.successful("Passes of client deleted");
    }
}
