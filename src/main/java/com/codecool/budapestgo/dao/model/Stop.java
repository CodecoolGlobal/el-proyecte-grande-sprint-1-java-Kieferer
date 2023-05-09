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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "stop_seq",
            sequenceName = "stop_seq",
            allocationSize = 1
    )
    private final Long id;
    @NonNull
    private String name;
    @Embedded
    @NonNull
    private Point location;
}
