package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.stop.NewStopDTO;
import com.codecool.budapestgo.controller.dto.stop.StopDTO;
import com.codecool.budapestgo.controller.dto.stop.UpdateStopDTO;
import com.codecool.budapestgo.customExceptionHandler.NotFoundException;
import com.codecool.budapestgo.dao.model.Schedule;
import com.codecool.budapestgo.dao.model.Stop;
import com.codecool.budapestgo.dao.repository.StopRepository;
import com.codecool.budapestgo.data.Point;
import com.codecool.budapestgo.utils.DtoMapper;
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
    void testGetAllStopsReturnsAllStopsWhenStopsExist() {
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
    void testGetAllStopsReturnsAllStopsWhenStopsNotExist() {
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
        Long id = 1L;
        Stop stop = buildStop(id, "Stop", new Point(1.0, 2.0));
        when(stopRepository.getStopById(id)).thenReturn(Optional.ofNullable(stop));

        ResponseEntity<String> response = stopService.deleteStopById(id);

        assertEquals("Deleted successfully", response.getBody());
        verify(stopRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteStopByIdWhenNotExists() {
        when(stopRepository.getStopById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> stopService.deleteStopById(1L));
        verify(stopRepository, never()).deleteById(anyLong());
    }

    @Test
    void testAddStop() {
        NewStopDTO stopDTO = new NewStopDTO("Stop", 1.0, 2.0);
        Stop stop = DtoMapper.toEntity(stopDTO);
        when(stopRepository.save(any(Stop.class))).thenReturn(stop);

        ResponseEntity<String> response = stopService.addStop(stopDTO);

        assertEquals("Stop created successfully", response.getBody());
        verify(stopRepository, times(1)).save(any(Stop.class));
    }


    @Test
    void testUpdateStopWhenStopExists() {
        Long stopId = 1L;
        Stop existingStop = buildStop(stopId, "Old Stop", new Point(1.0, 2.0));

        UpdateStopDTO updateStopDTO = new UpdateStopDTO(stopId, "New Stop", 2.0, 3.0);

        when(stopRepository.getStopById(stopId)).thenReturn(Optional.of(existingStop));

        ResponseEntity<String> response = stopService.updateStop(updateStopDTO);

        assertEquals("Stops updated successfully", response.getBody());
        verify(stopRepository, times(1)).save(any(Stop.class));
    }


    @Test
    void testUpdateStopWhenStopNotExists() {
        when(stopRepository.getStopById(anyLong())).thenReturn(Optional.empty());

        UpdateStopDTO updateStopDTO = new UpdateStopDTO(1L, "New Stop", 2.0, 3.0);

        assertThrows(NotFoundException.class,()->stopService.updateStop(updateStopDTO));
    }

    private Stop buildStop(Long id, String name, Point location) {
        return Stop.builder()
                .id(id)
                .name(name)
                .location(location)
                .schedules(new ArrayList<>())
                .build();
    }

    private void assertStopEquals(Stop stop, StopDTO stopDTO) {
        assertEquals(stop.getName(), stopDTO.name());
        assertEquals(stop.getLocation().getLatitude(), stopDTO.latitude());
        assertEquals(stop.getLocation().getLongitude(), stopDTO.longitude());
    }
}
//change
