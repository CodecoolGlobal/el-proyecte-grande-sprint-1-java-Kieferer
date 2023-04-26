package com.codecool.budapestgo.dao.model.pass;

import com.codecool.budapestgo.dao.model.client.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Pass {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private final Integer id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private final Client client;
    @Enumerated(EnumType.STRING)
    @NonNull
    private final PassType passType;
    @NonNull
    private final LocalDate startTime;
    @NonNull
    private final LocalDate expireTime;
    public static LocalDate calculateExpireTime(LocalDate startTime, PassType passType){
        return startTime.plusDays(passType.getExpireInDay());
    }

}
