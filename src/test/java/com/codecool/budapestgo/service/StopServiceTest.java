package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.repository.StopRepository;
import com.codecool.budapestgo.data.Point;
import com.codecool.budapestgo.utils.Response;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

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
    void testGetAllStopsWhenHasStops() {
        List<Stop> stops = new ArrayList<>();
        Stop stopOne = buildStop(1L, "Stop 1", new Point(1.0, 2.0));
        Stop stopTwo = buildStop(2L, "Stop 2", new Point(3.0, 4.0));
        stops.add(stopOne);
        stops.add(stopTwo);
        when(stopRepository.findAll()).thenReturn(stops);

        List<StopDTO> stopDTOs = stopService.getAllStops();

        assertEquals(stopDTOs.size(), stops.size());

        for (int i = 0; i < stops.size(); i++) {
            assertStopEquals(stops.get(i), stopDTOs.get(i));
        }
    }

    @Test
    void testGetAllStopsWhenHasNoStops() {
        List<Stop> stops = new ArrayList<>();
        when(stopRepository.findAll()).thenReturn(stops);

        List<StopDTO> stopDTOs = stopService.getAllStops();

        assertEquals(stopDTOs.size(), 0);

    }

    @Test
    void testGetStopByIdWhenFound() {
        Long id = 1L;
        Stop stop = buildStop(id, "Stop", new Point(1.0, 2.0));
        when(stopRepository.getStopById(id)).thenReturn(Optional.ofNullable(stop));

        Stop foundStop = stopService.getStopById(id);

        assertEquals(foundStop, stop);
    }

    @Test
    void testGetStopByIdWhenNotFound() {
        when(stopRepository.getStopById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> stopService.getStopById(1L));
    }

    @Test
    void testDeleteStopByIdWhenExists() {
        Long id= 1L;
        Stop stop = buildStop(id, "Stop", new Point(1.0, 2.0));
        when(stopRepository.getStopById(id)).thenReturn(Optional.ofNullable(stop));

        ResponseEntity<String> response = stopService.deleteStopById(id);

        assertEquals("Deleted successfully", response.getBody());
        verify(stopRepository, times(1)).deleteById(id);
    }

    /*
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
    */
    private Stop buildStop(Long id, String name, Point location) {
        return Stop.builder()
                .id(id)
                .name(name)
                .location(location)
                .build();
    }

    private void assertStopEquals(Stop stop, StopDTO stopDTO) {
        assertEquals(stop.getName(), stopDTO.name());
        assertEquals(stop.getLocation().getLatitude(), stopDTO.latitude());
        assertEquals(stop.getLocation().getLongitude(), stopDTO.longitude());
    }
}
