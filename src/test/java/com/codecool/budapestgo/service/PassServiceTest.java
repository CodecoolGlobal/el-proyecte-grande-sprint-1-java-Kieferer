package com.codecool.budapestgo.service;

import com.codecool.budapestgo.controller.dto.pass.PassDTO;
import com.codecool.budapestgo.controller.dto.pass.PassResponseDTO;
import com.codecool.budapestgo.dao.model.client.Client;
import com.codecool.budapestgo.dao.model.client.ClientRepository;
import com.codecool.budapestgo.dao.model.pass.Pass;
import com.codecool.budapestgo.dao.model.pass.PassRepository;
import com.codecool.budapestgo.dao.model.pass.PassType;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PassServiceTest {

    @Mock
    private PassRepository passRepository;

    @Mock
    private ClientRepository clientRepository;

    private PassService passService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        passService = new PassService(passRepository, clientRepository);
    }

    @Test
    void testGetAllPass() {
        List<Pass> passes = new ArrayList<>();
        Pass passOne = buildPass(1, PassType.WEEKLY);
        Pass passTwo = buildPass(2, PassType.MONTHLY);
        passes.add(passOne);
        passes.add(passTwo);
        when(passRepository.findAll()).thenReturn(passes);

        List<PassResponseDTO> passResponseDTOs = passService.getAllPass();

        assertEquals(passes.size(), passResponseDTOs.size());

        for (int i = 0; i < passes.size(); i++) {
            assertPassEquals(passes.get(i), passResponseDTOs.get(i));
        }
    }

    @Test
    void testAddPassWhenClientExists() {
        int clientId = 1;
        PassDTO passDTO = new PassDTO(clientId, "WEEKLY");
        Optional<Client> client = Optional.of(new Client());
        when(clientRepository.findById(clientId)).thenReturn(client);

        ResponseEntity<String> response = passService.addPass(passDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pass have been purchased", response.getBody());
        verify(passRepository, times(1)).save(argThat(pass ->
                pass.getClient().equals(client.get()) &&
                        pass.getPassType() == PassType.WEEKLY));
    }

    @Test
    void testAddPassWhenClientNotExists() {
        int clientId = 1;
        PassDTO passDTO = new PassDTO(clientId, "WEEKLY");
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = passService.addPass(passDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Client not found.", response.getBody());
        verify(passRepository, never()).save(any(Pass.class));
    }

    @Test
    void testGetExpiredPasses() {
        int clientId = 1;
        List<Pass> passes = new ArrayList<>();
        Pass passOne = buildPass(1, PassType.WEEKLY);
        Pass passTwo = buildPass(2, PassType.MONTHLY);
        passes.add(passOne);
        passes.add(passTwo);
        when(passRepository.getAllExpiredPassesByClient_id(clientId)).thenReturn(passes);

        List<PassResponseDTO> passResponseDTOs = passService.getExpiredPasses(clientId);

        assertEquals(passes.size(), passResponseDTOs.size());

        for (int i = 0; i < passes.size(); i++) {
            assertPassEquals(passes.get(i), passResponseDTOs.get(i));
        }
    }

    @Test
    void testGetActivePasses() {
        int clientId = 1;
        List<Pass> passes = new ArrayList<>();
        Pass passOne = buildPass(1, PassType.WEEKLY);
        Pass passTwo = buildPass(2, PassType.MONTHLY);
        passes.add(passOne);
        passes.add(passTwo);
        when(passRepository.getActivePassesByClient_id(clientId)).thenReturn(passes);

        List<PassResponseDTO> passResponseDTOs = passService.getActivePasses(clientId);

        assertEquals(passes.size(), passResponseDTOs.size());

        for (int i = 0; i < passes.size(); i++) {
            assertPassEquals(passes.get(i), passResponseDTOs.get(i));
        }
    }

    @Test
    void testPassExpirationForDailyPass() {
        int clientId = 1;
        PassDTO passDTO = new PassDTO(clientId, "DAILY");
        Optional<Client> client = Optional.of(new Client());
        when(clientRepository.findById(clientId)).thenReturn(client);

        LocalDate startDate = LocalDate.now();
        ResponseEntity<String> response = passService.addPass(passDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pass have been purchased", response.getBody());
        verify(passRepository, times(1)).save(argThat(pass ->
                pass.getClient().equals(client.get()) &&
                        pass.getPassType() == PassType.DAILY &&
                        pass.getStartTime().equals(startDate) &&
                        pass.getExpireTime().equals(startDate.plusDays(PassType.DAILY.getExpireInDay()))
        ));
    }

    @Test
    void testPassExpirationForWeeklyPass() {
        int clientId = 1;
        PassDTO passDTO = new PassDTO(clientId, "WEEKLY");
        Optional<Client> client = Optional.of(new Client());
        when(clientRepository.findById(clientId)).thenReturn(client);

        LocalDate startDate = LocalDate.now();
        ResponseEntity<String> response = passService.addPass(passDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pass have been purchased", response.getBody());
        verify(passRepository, times(1)).save(argThat(pass ->
                pass.getClient().equals(client.get()) &&
                        pass.getPassType() == PassType.WEEKLY &&
                        pass.getStartTime().equals(startDate) &&
                        pass.getExpireTime().equals(startDate.plusDays(PassType.WEEKLY.getExpireInDay()))
        ));
    }

    @Test
    void testPassExpirationForMonthlyPass() {
        int clientId = 1;
        PassDTO passDTO = new PassDTO(clientId, "MONTHLY");
        Optional<Client> client = Optional.of(new Client());
        when(clientRepository.findById(clientId)).thenReturn(client);

        LocalDate startDate = LocalDate.now();
        ResponseEntity<String> response = passService.addPass(passDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pass have been purchased", response.getBody());
        verify(passRepository, times(1)).save(argThat(pass ->
                pass.getClient().equals(client.get()) &&
                        pass.getPassType() == PassType.MONTHLY &&
                        pass.getStartTime().equals(startDate) &&
                        pass.getExpireTime().equals(startDate.plusDays(PassType.MONTHLY.getExpireInDay()))
        ));

    }

    private Pass buildPass(Integer id, PassType passType) {
        return Pass.builder()
                .id(id)
                .client(new Client())
                .passType(passType)
                .startTime(LocalDate.now())
                .expireTime(LocalDate.now().plusDays(passType.getExpireInDay()))
                .build();
    }

    private void assertPassEquals(Pass pass, PassResponseDTO passResponseDTO) {
        assertEquals(pass.getPassType().name(), passResponseDTO.getPassType());
        assertEquals(pass.getStartTime(), passResponseDTO.getStartDate());
        assertEquals(pass.getExpireTime(), passResponseDTO.getExpirationDate());
    }
}
