package com.codecool.budapestgo.service;


import com.codecool.budapestgo.controller.dto.stop.NewStopDTO;
import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.controller.dto.stop.UpdateStopDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.repository.StopRepository;
import com.codecool.budapestgo.utils.DtoMapper;
import com.codecool.budapestgo.utils.Response;
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

    public List<StopDTO> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(stop -> new StopDTO(stop.getId(), stop.getName(),stop.getLocation().getLatitude(),stop.getLocation().getLongitude()))
                .toList();
    }

    public ResponseEntity<String> deleteStopById(Long id) {
        checkStopExistenceById(id);
        stopRepository.deleteById(id);
        return Response.successful("Deleted");
    }

    public ResponseEntity<String> addStop(NewStopDTO newStopDTO) {
            Stop stop = DtoMapper.toEntity(newStopDTO);
            stopRepository.save(stop);
            return Response.created("Stop");
    }

    public ResponseEntity<String> updateStop(UpdateStopDTO updateStopDTO) {
        Stop stop = getStopById(updateStopDTO.id());
        List<Schedule> schedules = new ArrayList<>(stop.getSchedules());
        schedules.forEach(stop::removeSchedule);

        Stop updatedStop = DtoMapper.toEntity(updateStopDTO);
        schedules.forEach(updatedStop::addSchedule);

        stopRepository.save(updatedStop);
        return Response.successful("Stops updated");
    }

    public ResponseEntity<String> deleteAllStops() {
            stopRepository.deleteAll();
            return Response.successful("Stops deleted");
    }
    private void checkStopExistenceById(long id){
        stopRepository.getStopById(id).orElseThrow(() -> new NotFoundException("Stop with id " + id));
    }
    private Stop getStopById(long id){
        return stopRepository.getStopById(id).orElseThrow(() -> new NotFoundException("Stop with " + id));
    }
}
