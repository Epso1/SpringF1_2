package org.api.springf1.service;

import org.api.springf1.dto.ConstructorDTO;
import org.api.springf1.exception.ConstructorNotFoundException;
import org.api.springf1.model.Constructor;
import org.api.springf1.repository.ConstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConstructorServiceImplTest {

    @Mock
    ConstructorRepository constructorRepository;

    @InjectMocks
    ConstructorServiceImpl driverService;

    Constructor constructor;
    ConstructorDTO constructorDTO;


    @BeforeEach
    void setUp() {
        constructor = Constructor.builder().id(1L).ref("MCD").name("Mercedes").nationality("German").url("http://mercedes.com").build();
        constructorDTO = ConstructorDTO.builder().id(1L).ref("MCD").name("Mercedes").build();
    }


    @Test
    void shouldReturnConstructorListWhenGetAllConstructors() {
        //Given
        when(constructorRepository.findAll()).thenReturn(List.of(constructor));
        // When
        List<Constructor> constructorList = constructorRepository.findAll();
        // Then
        assertEquals(1, constructorList.size());
        assertEquals(constructor, constructorList.get(0));
    }

    @Test
    void shouldReturnConstructorDTOWhenGetConstructorByRef() {
        //Given
        when(constructorRepository.findByRefIgnoreCase("MCD")).thenReturn(Optional.of(constructor));
        // When
        ConstructorDTO constructorByRef = driverService.getConstructorByRef("MCD");
        // Then
        assertEquals(constructorDTO, constructorByRef);
    }

    @Test
    void shouldReturnConstructorDTOWhenSaveConstructor(){
        //Given
        when(constructorRepository.save(constructor)).thenReturn(constructor);
        // When
        ConstructorDTO savedConstructor = driverService.saveConstructor(constructor);
        // Then
        assertEquals(constructorDTO, savedConstructor);
    }

    @Test
    void shouldReturnConstructorDTOWhenUpdateConstructor(){
        //Given
        when(constructorRepository.findByRefIgnoreCase("MCD")).thenReturn(Optional.of(constructor));
        when(constructorRepository.save(constructor)).thenReturn(constructor);
        // When
        ConstructorDTO updatedConstructor = driverService.updateConstructor(constructor);
        // Then
        assertEquals(constructorDTO, updatedConstructor);
    }

    @Test
    void shouldReturnNothingWhenDeleteConstructorByRef(){
        //Given
        when(constructorRepository.findByRefIgnoreCase("MCD")).thenReturn(Optional.of(constructor));
        // When
        driverService.deleteConstructorByRef("MCD");
        // Then
        assertDoesNotThrow(() -> driverService.deleteConstructorByRef("MCD"));
    }

    @Test
    void shouldReturnEmptyConstructorListWhenGetAllConstructorsIsEmpty() {
        //Given
        when(constructorRepository.findAll()).thenReturn(List.of());
        // When
        List<Constructor> constructorList = constructorRepository.findAll();
        // Then
        assertEquals(0, constructorList.size());
    }



    @Test
    void shouldReturnConstructorNotFoundExceptionWhenGetConstructorByRefNotFound() {
        //Given
        when(constructorRepository.findByRefIgnoreCase("MCD")).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ConstructorNotFoundException.class, () -> driverService.getConstructorByRef("MCD"));
    }

    @Test
    void shouldReturnConstructorNotFoundExceptionWhenUpdateConstructorNotFound(){
        //Given
        when(constructorRepository.findByRefIgnoreCase("MCD")).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ConstructorNotFoundException.class, () -> driverService.updateConstructor(constructor));
    }

    @Test
    void shouldReturnConstructorNotFoundExceptionWhenDeleteConstructorByRefNotFound(){
        //Given
        when(constructorRepository.findByRefIgnoreCase("MCD")).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ConstructorNotFoundException.class, () -> driverService.deleteConstructorByRef("MCD"));
    }





}