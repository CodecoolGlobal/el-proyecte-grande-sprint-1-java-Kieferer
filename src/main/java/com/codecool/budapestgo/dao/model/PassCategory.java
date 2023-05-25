package com.codecool.budapestgo.dao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PassCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    @NonNull private final String passDuration;
    @NonNull private final Long passExpireInDay;
    @NonNull private final String category;
    @NonNull private final Integer price;

}
