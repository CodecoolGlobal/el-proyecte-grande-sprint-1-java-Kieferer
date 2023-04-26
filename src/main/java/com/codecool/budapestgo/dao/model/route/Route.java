package com.codecool.budapestgo.dao.model.route;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class Route {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private final Integer id;

    @NonNull private  String name;

    public void setName(@NonNull String name){
        this.name = name;
    }
}