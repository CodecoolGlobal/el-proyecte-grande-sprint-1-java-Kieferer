package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Stop;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {
    Optional<Stop> getStopByName(@NonNull String name);
}
