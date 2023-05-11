package com.codecool.budapestgo.dao.model;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "route", referencedColumnName = "id")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stop", referencedColumnName = "id")
    private Stop stop;
}
