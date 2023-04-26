package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.pass.Pass;
import lombok.Getter;

@Getter
public class PassDTO {
    private final int clientId;
    private final String passType;

    public PassDTO(int clientId, String passType) {
        this.clientId = clientId;
        this.passType = passType;
    }
    public static PassDTO of(Pass pass){
        return new PassDTO(pass.getClient_id(),pass.getPassType().toString());
    }
}
