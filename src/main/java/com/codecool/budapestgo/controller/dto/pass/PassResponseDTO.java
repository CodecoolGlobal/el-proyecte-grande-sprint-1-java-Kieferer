package com.codecool.budapestgo.controller.dto.pass;

import com.codecool.budapestgo.dao.model.Pass;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;
@Getter
public final class PassResponseDTO {
    private final String passType;
    private final LocalDate startDate;
    private final LocalDate expirationDate;

    public PassResponseDTO(String passType, LocalDate startDate, LocalDate expirationDate) {
        this.passType = passType;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
    }

    public static PassResponseDTO of(Pass pass){
        return new PassResponseDTO(pass.getPassType().toString(),pass.getStartTime(),pass.getExpireTime());
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PassResponseDTO) obj;
        return Objects.equals(this.passType, that.passType) &&
                Objects.equals(this.startDate, that.startDate) &&
                Objects.equals(this.expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passType, startDate, expirationDate);
    }

    @Override
    public String toString() {
        return "PassResponseDTO[" +
                "passType=" + passType + ", " +
                "startDate=" + startDate + ", " +
                "expirationDate=" + expirationDate + ']';
    }

}
