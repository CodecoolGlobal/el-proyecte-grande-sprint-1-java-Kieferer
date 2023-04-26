package com.codecool.budapestgo.dao.model.schedule;

import com.codecool.budapestgo.dao.model.route.Route;
import com.codecool.budapestgo.dao.model.stop.Stop;
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
    private final int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "route_name", referencedColumnName = "name")
    private Route route;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "stop_name", referencedColumnName = "name")
    private Stop stop;

    //duration, launch_date
}
