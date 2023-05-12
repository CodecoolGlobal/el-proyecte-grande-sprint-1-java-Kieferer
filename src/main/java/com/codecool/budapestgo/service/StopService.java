package com.codecool.budapestgo.service;


import com.codecool.budapestgo.controller.dto.stop.NewStopDTO;
import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.controller.dto.stop.UpdateStopDTO;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.data.Point;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.repository.StopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StopService {
    private final StopRepository stopRepository;

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }
    private boolean existsByName(String name) {
        return stopRepository.getStopByName(name).isPresent();
    }
    public List<StopDTO> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(stop -> new StopDTO(stop.getId(), stop.getName(),stop.getLocation().getLatitude(),stop.getLocation().getLongitude()))
                .toList();
    }

    public ResponseEntity<StopDTO> getStopById(Long id) {
        return stopRepository.findById(id)
                .stream()
                .map(stop ->  ResponseEntity.ok(new StopDTO(stop.getId(), stop.getName(),stop.getLocation().getLatitude(),stop.getLocation().getLongitude())))
                .findFirst()
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> deleteStopById(Long id) {
        if(stopRepository.existsById(id)) {
            stopRepository.deleteById(id);
            return ResponseEntity.ok("Stop deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Stop not found.");
    }

    public ResponseEntity<String> addStop(NewStopDTO newStopDTO) {
        if(!existsByName(newStopDTO.name())) {
            Stop stop = Stop.builder()
                    .name(newStopDTO.name())
                    .location(new Point(newStopDTO.latitude(), newStopDTO.longitude()))
                    .schedules(new ArrayList<>())
                    .build();
            stopRepository.save(stop);
            return ResponseEntity.ok("Stop created");
        }
        return ResponseEntity.badRequest()
                .body("Stop already exist.");
    }

    public ResponseEntity<String> updateStop(UpdateStopDTO updateStopDTO) {
        Stop stop = stopRepository.getStopById(updateStopDTO.id()).orElseThrow(() -> new RuntimeException("There is no existing Stop in this ID so it can't be updated. " + updateStopDTO.id()));
        List<Schedule> schedules = new ArrayList<>(stop.getSchedules());
        schedules.forEach(stop::removeSchedule);

        Stop updatedStop = Stop.builder()
                .id(updateStopDTO.id())
                .name(updateStopDTO.name())
                .location(new Point(updateStopDTO.latitude(), updateStopDTO.longitude()))
                .schedules(new ArrayList<>())
                .build();

        schedules.forEach(updatedStop::addSchedule);

        stopRepository.save(updatedStop);
        return ResponseEntity.ok("Stop updated");
    }

    public ResponseEntity<String> deleteAllStops() {
            stopRepository.deleteAll();
            return ResponseEntity.ok("Stops deleted");
    }
}
