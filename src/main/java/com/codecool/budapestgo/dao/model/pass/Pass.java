package com.codecool.budapestgo.dao.model.pass;

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
    private final Integer pass_id;
   /* @OneToOne()
    @JoinColumn(table = "client", name = "id", referencedColumnName = "id")
    private final Client client;*/
    @NonNull
    private final Integer client_id;
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
