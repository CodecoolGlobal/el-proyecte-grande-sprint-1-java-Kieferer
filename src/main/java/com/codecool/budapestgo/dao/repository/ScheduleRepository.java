package com.codecool.budapestgo.dao.repository;

import com.codecool.budapestgo.dao.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE s.route.name = :name")
    List<Schedule> findByRouteName(@Param("name") String name);
}
