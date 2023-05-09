package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Stop;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop, Long> {
    Optional<Stop> getStopByName(@NonNull String name);
}
