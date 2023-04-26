package com.codecool.budapestgo.service;


import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.dao.model.stop.Point;
import com.codecool.budapestgo.dao.model.stop.Stop;
import com.codecool.budapestgo.dao.model.stop.StopRepository;
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

    public List<StopDTO> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(stop -> new StopDTO(stop.getName(),stop.getLocation().getLatitude(),stop.getLocation().getLongitude()))
                .toList();
    }

    public ResponseEntity<StopDTO> getStopById(Integer id) {
        return stopRepository.findById(id)
                .stream()
                .map(stop ->  ResponseEntity.ok(new StopDTO(stop.getName(),stop.getLocation().getLatitude(),stop.getLocation().getLongitude())))
                .findFirst()
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public void deleteStopById(Integer id) {
        stopRepository.deleteById(id);
    }

    public ResponseEntity<String> addStop(StopDTO stopDTO) {
       Optional<Stop> searchedStop = stopRepository.getStopByName(stopDTO.name());
       if(searchedStop.isEmpty()) {
           Stop stop = Stop.builder()
                   .name(stopDTO.name())
                   .location(new Point(stopDTO.latitude(), stopDTO.longitude()))
                   .build();
           stopRepository.save(stop);
           return ResponseEntity.ok("Stop created");
       }
       return ResponseEntity.badRequest().body("Stop is already exist.");
    }

    public ResponseEntity<String> updateStop(StopDTO stopDTO) {
        Optional<Stop> stop = stopRepository.getStopByName(stopDTO.name());
        if (stop.isPresent()) {
            stop.get().setLocation(new Point(stopDTO.latitude(),stopDTO.longitude()));
            stopRepository.save(stop.get());
            return ResponseEntity.ok("Stop updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stop not found");
    }
}
