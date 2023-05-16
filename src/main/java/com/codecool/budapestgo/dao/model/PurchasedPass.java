package com.codecool.budapestgo.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PurchasedPass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private final Client client;
    @ManyToOne
    @JoinColumn(name = "pass_id", referencedColumnName = "id")
    private final PassCategory passCategory;
    private final LocalDate expireTime;

    public static LocalDate calculateExpireTime(long numberOfDays){
        return LocalDate.now().plusDays(numberOfDays);
    }
}
