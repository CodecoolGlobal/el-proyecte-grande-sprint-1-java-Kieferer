package com.codecool.budapestgo.dao.model;

import com.codecool.budapestgo.data.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
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
    @SequenceGenerator(
            name = "stop_seq",
            sequenceName = "stop_seq",
            allocationSize = 1
    )
    private final Long id;
    @NonNull
    private String name;
    @Embedded
    @NonNull
    private Point location;
    @JsonIgnore
    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        schedule.setStop(this);
    }
    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        schedule.setStop(null);
    }
}
