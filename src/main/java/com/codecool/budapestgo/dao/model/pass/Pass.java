package com.codecool.budapestgo.dao.model.pass;

import com.codecool.budapestgo.dao.model.client.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Pass {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private final Integer id;
    @OneToOne()
    @JoinColumn(table = "client", name = "id")
    private final Client client;


}
