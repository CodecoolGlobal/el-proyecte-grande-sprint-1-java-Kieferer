package com.codecool.budapestgo.dao.model.schedule;

import com.codecool.budapestgo.dao.model.route.Route;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
@NoArgsConstructor(force = true)
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private final int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "routeId", referencedColumnName = "id", nullable = false)
    private Route route;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "stopId", referencedColumnName = "id", nullable = false)
    private Route stop;
}
