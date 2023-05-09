package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE s.route.name = :name")
    List<Schedule> findByRouteName(@Param("name") String name);
}
