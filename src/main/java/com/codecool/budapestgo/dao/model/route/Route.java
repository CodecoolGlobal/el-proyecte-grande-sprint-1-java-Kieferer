package com.codecool.budapestgo.dao.model.route;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private final Integer id;
    @NonNull
    private String name;
    public void setName(@NonNull String name){
        this.name = name;
    }
}
