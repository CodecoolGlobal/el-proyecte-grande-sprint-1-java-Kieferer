package com.codecool.budapestgo.dao.model.stop;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop,Integer> {
    Optional<Stop> getStopByName(@NonNull String name);
}
