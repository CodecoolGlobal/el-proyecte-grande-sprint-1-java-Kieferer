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
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_name", referencedColumnName = "name")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stop_name", referencedColumnName = "name")
    private Stop stop;
}
