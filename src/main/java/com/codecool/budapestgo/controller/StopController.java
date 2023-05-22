package com.codecool.budapestgo.controller;

import com.codecool.budapestgo.controller.dto.stop.NewStopDTO;
import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.controller.dto.stop.UpdateStopDTO;
import com.codecool.budapestgo.service.StopService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stop")
@RequiredArgsConstructor
public class StopController {
    private final StopService stopService;

    @GetMapping("/all")
    public List<StopDTO> getAllStop(){
        return stopService.getAllStops();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStop(@Valid @PathVariable @Min(1) Long id){
        return stopService.deleteStopById(id);
    }
    @PostMapping("/add")
    public ResponseEntity<String> registerStop(@Valid @RequestBody NewStopDTO newStopDTO) {
        return stopService.addStop(newStopDTO);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateStop(@Valid @RequestBody UpdateStopDTO updateStopDTO){
        return stopService.updateStop(updateStopDTO);
    }
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllStop(){
        return stopService.deleteAllStops();
    }
}
