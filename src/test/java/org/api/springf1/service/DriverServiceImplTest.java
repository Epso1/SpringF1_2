package org.api.springf1.service;

import org.api.springf1.dto.DriverDTO;
import org.api.springf1.dto.DriverResponse;
import org.api.springf1.exception.DriverNotFoundException;
import org.api.springf1.model.Driver;
import org.api.springf1.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @Mock
    DriverRepository driverRepository;

    @InjectMocks
    DriverServiceImpl driverService;

    Driver driver;
    DriverDTO driverDTO;

    @BeforeEach
    void setUp() {
        driver = Driver.builder().id(1L).code("HAM").forename("Lewis").surname("Hamilton").build();
        driverDTO = DriverDTO.builder().id(1L).code("HAM").forename("Lewis").surname("Hamilton").build();
    }

    @Test
    void shouldReturnDriverDTOWhenFindDriverByCode() {
        //Given
        when(driverRepository.findByCodeIgnoreCase("HAM")).thenReturn(Optional.of(driver));
        // When
        DriverDTO driverByCode = driverService.getDriverByCode("HAM");
        // Then
        assertEquals(driverDTO, driverByCode);

    }

    @Test
    void shouldReturnDriverDTOWhenUpdateDriver(){
        //Given
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(driver);
        // When
        DriverDTO updatedDriver = driverService.updateDriver(driver);
        // Then
        assertEquals(driverDTO, updatedDriver);
    }

    @Test
    void shouldReturnNothingWhenDeleteDriverByCode(){
        //Given
        when(driverRepository.findByCodeIgnoreCase("HAM")).thenReturn(Optional.of(driver));
        // When
        driverService.deleteDriverByCode("HAM");
        // Then
        assertDoesNotThrow(() -> driverService.deleteDriverByCode("HAM"));
    }

    @Test
    void shouldReturnDriverResponseWhenGetAllDrivers(){
        //Given
        Pageable pageable = PageRequest.of(0, 1);
        Page<Driver> page = new PageImpl<>(List.of(driver));
        when(driverRepository.findAll(pageable)).thenReturn(page);
        // When
        DriverResponse drivers = driverService.getDrivers(0, 1);
        // Then
        assertEquals(1, drivers.totalElements());
    }

    @Test
    void shouldReturnDriverDTOWhenSaveDriver(){
        //Given
        when(driverRepository.save(driver)).thenReturn(driver);
        // When
        DriverDTO savedDriver = driverService.saveDriver(driver);
        // Then
        assertEquals(driverDTO, savedDriver);
    }

    @Test
    void shouldThrowDriverNotFoundExceptionWhenDriverNotFound(){
        //Given
        when(driverRepository.findByCodeIgnoreCase("HAM")).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(DriverNotFoundException.class, () -> driverService.getDriverByCode("HAM"));
    }


    @Test
    void shouldThrowDriverNotFoundExceptionWhenDriverNotFoundForUpdate(){
        //Given
        when(driverRepository.findById(1L)).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(DriverNotFoundException.class, () -> driverService.updateDriver(driver));
    }

    @Test
    void shouldThrowDriverNotFoundExceptionWhenDriverNotFoundForDelete(){
        //Given
        when(driverRepository.findByCodeIgnoreCase("HAM")).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(DriverNotFoundException.class, () -> driverService.deleteDriverByCode("HAM"));
    }



}