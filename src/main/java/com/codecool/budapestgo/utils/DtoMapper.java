package com.codecool.budapestgo.utils;

import com.codecool.budapestgo.controller.dto.pass.PassCategoryRegisterDTO;
import com.codecool.budapestgo.controller.dto.pass.PassCategoryResponseDTO;
import com.codecool.budapestgo.controller.dto.stop.NewStopDTO;
import com.codecool.budapestgo.controller.dto.stop.UpdateStopDTO;
import com.codecool.budapestgo.dao.model.Client;
import com.codecool.budapestgo.dao.model.PassCategory;
import com.codecool.budapestgo.dao.model.PurchasedPass;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.types.Role;
import com.codecool.budapestgo.data.Point;
import com.codecool.budapestgo.security.auth.RegisterRequest;

import java.util.ArrayList;

public class DtoMapper {
    public static Client toEntity(RegisterRequest request,String password){
        return Client.builder()
                .email(request.getEmail())
                .password(password)
                .role(Role.CUSTOMER)
                .build();
    }
    public static Stop toEntity(NewStopDTO newStopDTO){
        return Stop.builder()
                .name(newStopDTO.name())
                .location(new Point(newStopDTO.latitude(), newStopDTO.longitude()))
                .schedules(new ArrayList<>())
                .build();
    }
    public static Stop toEntity(UpdateStopDTO updateStopDTO){
        return Stop.builder()
                .id(updateStopDTO.id())
                .name(updateStopDTO.name())
                .location(new Point(updateStopDTO.latitude(), updateStopDTO.longitude()))
                .schedules(new ArrayList<>())
                .build();
    }
    public static PassCategory toEntity(PassCategoryResponseDTO passCategoryResponseDTO){
        return PassCategory.builder()
                .id(passCategoryResponseDTO.id())
                .passDuration(passCategoryResponseDTO.passDuration())
                .passExpireInDay(passCategoryResponseDTO.passExpireInDay())
                .category(passCategoryResponseDTO.category())
                .price(passCategoryResponseDTO.price())
                .build();
    }
    public static PassCategory toEntity(PassCategoryRegisterDTO passCategoryRegisterDTO) {
        return PassCategory.builder()
                .passDuration(passCategoryRegisterDTO.passDuration())
                .passExpireInDay(passCategoryRegisterDTO.passExpireInDay())
                .category(passCategoryRegisterDTO.category())
                .price(passCategoryRegisterDTO.price())
                .build();
    }

    public static PurchasedPass toEntity(Client client, PassCategory passCategory) {
        long expireInDays = passCategory.getPassExpireInDay();

        return PurchasedPass.builder()
                .client(client)
                .passCategory(passCategory)
                .expireTime(PurchasedPass.calculateExpireTime(expireInDays))
                .build();
    }
}
