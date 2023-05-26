package com.codecool.budapestgo.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PurchasedPass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client", referencedColumnName = "email")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass", referencedColumnName = "id")
    private final PassCategory passCategory;
    private final LocalDate expireTime;

    public static LocalDate calculateExpireTime(long numberOfDays){
        return LocalDate.now().plusDays(numberOfDays);
    }
}
