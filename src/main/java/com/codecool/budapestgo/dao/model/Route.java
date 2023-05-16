package com.codecool.budapestgo.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    @NonNull
    private final String name;
    @JsonIgnore
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        schedule.setRoute(this);
    }
    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        schedule.setRoute(null);
    }
}
