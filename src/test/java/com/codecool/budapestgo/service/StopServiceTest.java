package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.dao.model.stop.Point;
import com.codecool.budapestgo.dao.model.stop.Stop;
import com.codecool.budapestgo.dao.model.stop.StopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StopServiceTest {

    @Mock
    private StopRepository stopRepository;

    private StopService stopService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        stopService = new StopService(stopRepository);
    }

    @Test
    void testExistsById() {
        int id = 1;
        when(stopRepository.findById(id)).thenReturn(Optional.of(new Stop()));

        assertTrue(stopService.existsById(id));
    }

    @Test
    void testExistsByIdWhenNotExists() {
        int id = 1;
        when(stopRepository.findById(id)).thenReturn(Optional.empty());

        assertFalse(stopService.existsById(id));
    }

    @Test
    void testExistsByName() {
        String name = "Stop";
        when(stopRepository.getStopByName(name)).thenReturn(Optional.of(new Stop()));

        assertTrue(stopService.existsByName(name));
    }

    @Test
    void testExistsByNameWhenNotExists() {
        String name = "Stop";
        when(stopRepository.getStopByName(name)).thenReturn(Optional.empty());

        assertFalse(stopService.existsByName(name));
    }

    @Test
    void testGetAllStops() {
        List<Stop> stops = new ArrayList<>();
        Stop stopOne = new Stop(1, "Stop 1", new Point(1.0, 2.0));
        Stop stopTwo = new Stop(2, "Stop 2", new Point(3.0, 4.0));
        stops.add(stopOne);
        stops.add(stopTwo);
        when(stopRepository.findAll()).thenReturn(stops);

        List<StopDTO> stopDTOs = stopService.getAllStops();

        assertEquals(stopDTOs.size(), stops.size());
        assertEquals(stops.get(0).getName(), stopDTOs.get(0).name());
        assertEquals(stops.get(0).getLocation().getLatitude(), stopDTOs.get(0).latitude());
        assertEquals(stops.get(0).getLocation().getLongitude(), stopDTOs.get(0).longitude());
        assertEquals(stops.get(1).getName(), stopDTOs.get(1).name());
        assertEquals(stops.get(1).getLocation().getLatitude(), stopDTOs.get(1).latitude());
        assertEquals(stops.get(1).getLocation().getLongitude(), stopDTOs.get(1).longitude());
    }

    @Test
    void testGetStopByIdWhenFound() {
        int id = 1;
        Stop stop = new Stop(id, "Stop", new Point(1.0, 2.0));
        when(stopRepository.findById(id)).thenReturn(Optional.of(stop));

        ResponseEntity<StopDTO> response = stopService.getStopById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stop.getName(), response.getBody().name());
        assertEquals(stop.getLocation().getLatitude(), response.getBody().latitude());
        assertEquals(stop.getLocation().getLongitude(), response.getBody().longitude());
    }

    @Test
    void testGetStopByIdWhenNotFound() {
        int id = 1;
        when(stopRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<StopDTO> response = stopService.getStopById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteStopByIdWhenExists() {
        int id = 1;
        when(stopRepository.existsById(id)).thenReturn(true);

        ResponseEntity<String> response = stopService.deleteStopById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Stop deleted.", response.getBody());
        verify(stopRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteStopByIdWhenNotExists() {
        int id = 1;
        when(stopRepository.existsById(id)).thenReturn(false);

        ResponseEntity<String> response = stopService.deleteStopById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Stop not found.", response.getBody());
        verify(stopRepository, never()).deleteById(anyInt());
    }

    @Test
    void testAddStopWhenNotExists() {
        StopDTO stopDTO = new StopDTO("Stop", 1.0, 2.0);
        when(stopRepository.getStopByName(stopDTO.name())).thenReturn(Optional.empty());

        ResponseEntity<String> response = stopService.addStop(stopDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Stop created", response.getBody());
        verify(stopRepository, times(1)).save(argThat(stop ->
                stop.getName().equals(stopDTO.name()) &&
                        stop.getLocation().getLatitude() == stopDTO.latitude() &&
                        stop.getLocation().getLongitude() == stopDTO.longitude()));
    }

    @Test
    void testAddStopWhenExists() {
        StopDTO stopDTO = new StopDTO("Stop", 1.0, 2.0);
        when(stopRepository.getStopByName(stopDTO.name())).thenReturn(Optional.of(new Stop()));

        ResponseEntity<String> response = stopService.addStop(stopDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Stop already exist.", response.getBody());
        verify(stopRepository, never()).save(any(Stop.class));
    }

    @Test
    void testUpdateStopWhenStopExists() {
        StopDTO stopDTO = new StopDTO("Stop", 1.0, 2.0);
        Stop stop = new Stop(1, "Old Stop", new Point(3.0, 4.0));
        when(stopRepository.getStopByName(stopDTO.name())).thenReturn(Optional.of(stop));
        when(stopRepository.save(stop)).thenReturn(stop);

        ResponseEntity<String> response = stopService.updateStop(stopDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Stop updated", response.getBody());
        assertEquals(stopDTO.latitude(), stop.getLocation().getLatitude());
        assertEquals(stopDTO.longitude(), stop.getLocation().getLongitude());
    }

    @Test
    void testUpdateStopWhenStopNotExists() {
        StopDTO stopDTO = new StopDTO("Stop", 1.0, 2.0);
        when(stopRepository.getStopByName(stopDTO.name())).thenReturn(Optional.empty());

        ResponseEntity<String> response = stopService.updateStop(stopDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Stop not found", response.getBody());
        verify(stopRepository, never()).save(any(Stop.class));
    }
}
