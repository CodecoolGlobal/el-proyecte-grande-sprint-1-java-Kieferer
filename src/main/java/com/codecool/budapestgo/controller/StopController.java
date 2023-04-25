package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.service.StopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stop")
public class StopController {
    private final StopService stopService;

    public StopController(StopService stopService) {
        this.stopService = stopService;
    }

    @GetMapping("/all")
    public List<StopDTO> getAllStop(){
        return stopService.getAllStops();
    }
    @DeleteMapping("/{id}")
    public void deleteStop(@PathVariable int id){
        stopService.deleteStopById(id);
    }
    @PostMapping("/register")
    public void registerStop(@RequestBody StopDTO stopDTO){
        stopService.addStop(stopDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStop(@RequestBody StopDTO stopDTO){
        return stopService.updateStop(stopDTO);
    }
}