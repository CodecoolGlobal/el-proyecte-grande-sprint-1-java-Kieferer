package com.codecool.budapestgo.dao.model.stop;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
    @NonNull private String name;
    @Embedded
    @NonNull private Point location;
}
