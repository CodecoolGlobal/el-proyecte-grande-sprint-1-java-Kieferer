package com.codecool.budapestgo.dao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NonNull
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PassCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "pass_seq",
            sequenceName = "pass_seq",
            allocationSize = 1
    )
    private Long id;
    private final String passDuration;
    private final long passExpireInDay;
    private final String category;
    private final int price;

}
