package com.codecool.budapestgo.service;


import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.data.Point;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.repository.StopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StopService {
    private final StopRepository stopRepository;

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }
    public boolean existsByName(String name) {
        return stopRepository.getStopByName(name).isPresent();
    }
    public List<StopDTO> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(stop -> new StopDTO(stop.getName(),stop.getLocation().getLatitude(),stop.getLocation().getLongitude()))
                .toList();
    }

    public ResponseEntity<StopDTO> getStopById(Long id) {
        return stopRepository.findById(id)
                .stream()
                .map(stop ->  ResponseEntity.ok(new StopDTO(stop.getName(),stop.getLocation().getLatitude(),stop.getLocation().getLongitude())))
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

    public ResponseEntity<String> addStop(StopDTO stopDTO) {
        if(!existsByName(stopDTO.name())) {
            Stop stop = Stop.builder()
                    .name(stopDTO.name())
                    .location(new Point(stopDTO.latitude(), stopDTO.longitude()))
                    .build();
            stopRepository.save(stop);
            return ResponseEntity.ok("Stop created");
        }
        return ResponseEntity.badRequest()
                .body("Stop already exist.");
    }

    public ResponseEntity<String> updateStop(StopDTO stopDTO) {
        Optional<Stop> stop = stopRepository.getStopByName(stopDTO.name());
        if (stop.isPresent()) {
            stop.get().setLocation(new Point(stopDTO.latitude(),stopDTO.longitude()));
            stopRepository.save(stop.get());
            return ResponseEntity.ok("Stop updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Stop not found");
    }

    public ResponseEntity<String> deleteAllStops() {
            stopRepository.deleteAll();
            return ResponseEntity.ok("Stops deleted");
    }
}
