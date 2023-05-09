package com.codecool.budapestgo.dao.model;

import com.codecool.budapestgo.data.Point;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor()
@Entity
public class Stop {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private final Integer id;
    @NonNull
    private String name;
    @Embedded
    @NonNull
    private Point location;
}
