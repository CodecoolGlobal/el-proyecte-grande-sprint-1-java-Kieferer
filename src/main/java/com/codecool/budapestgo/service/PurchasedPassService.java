package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PurchasedPassResponseDTO;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.repository.ClientRepository;
import com.codecool.budapestgo.dao.model.PurchasedPass;
import com.codecool.budapestgo.dao.repository.PassCategoryRepository;
import com.codecool.budapestgo.dao.repository.PurchasedPassRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PurchasedPassService {
    private final PurchasedPassRepository purchasedPassRepository;
    private final PassCategoryRepository passCategoryRepository;
    private final ClientRepository clientRepository;

    public List<PurchasedPassResponseDTO> getAllPass(){
        return purchasedPassRepository.findAll()
                .stream()
                .map(PurchasedPassResponseDTO::of)
                .toList();
    }

    public ResponseEntity<String> addPass(PassDTO passDTO){
        Optional<Client> client = clientRepository.findById(passDTO.clientId());
        Optional<PassCategory> pass = passCategoryRepository.findByCategoryAndPassDuration(passDTO.passCategory(),passDTO.passDuration());

        if(client.isPresent() && pass.isPresent()) {
            long expireInDays = pass.get().getPassExpireInDay();
            PurchasedPass purchasedPass = PurchasedPass.builder()
                    .client(client.get())
                    .passCategory(pass.get())
                    .expireTime(PurchasedPass.calculateExpireTime(expireInDays))
                    .build();

            purchasedPassRepository.save(purchasedPass);
            return ResponseEntity.ok("PassCategory have been purchased");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Client or passCategory not found.");
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
    public ResponseEntity<String> deletePassesByClientId(Long id){
        return purchasedPassRepository.deleteAllByClient_Id(id) ?
                ResponseEntity.ok("Passes have been deleted.") :
                ResponseEntity.badRequest().body("There is no pass to delete.");
    }

}
