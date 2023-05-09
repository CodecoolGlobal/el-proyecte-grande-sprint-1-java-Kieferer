package com.codecool.budapestgo.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "schedule_seq",
            sequenceName = "schedule_seq",
            allocationSize = 1
    )
    private final Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "route_name", referencedColumnName = "name")
    private Route route;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "stop_name", referencedColumnName = "name")
    private Stop stop;
}
