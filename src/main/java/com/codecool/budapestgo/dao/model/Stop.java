package com.codecool.budapestgo.dao.model;

import com.codecool.budapestgo.data.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor()
@Entity
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private final Long id;
    @NonNull
    private String name;
    @Embedded
    @NonNull
    private Point location;
    @JsonIgnore
    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        schedule.setStop(this);
    }
    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        schedule.setStop(null);
    }
}
