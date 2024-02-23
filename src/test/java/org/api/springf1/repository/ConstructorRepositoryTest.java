package org.api.springf1.repository;

import org.api.springf1.model.Constructor;
import org.api.springf1.model.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@DataJpaTest
class ConstructorRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.5");


    @Autowired ConstructorRepository constructorRepository;

    Constructor constructor;
    Constructor constructor2;

    @BeforeEach
    void setup(){
        constructor = Constructor.builder().ref("FER").name("Ferrari").nationality("Italian").url("https://www.ferrari.com").build();
        constructor2 = Constructor.builder().ref("MCL").name("McLaren").nationality("British").url("https://www.mclaren.com").build();
    }



    @Test
    void shouldReturnSavedConstructorWhenSave() {
        //Given
        // When
        Constructor constructorSaved = constructorRepository.save(constructor);
        // Then
        assertThat(constructorSaved).isNotNull();
        assertThat(constructorSaved.getId()).isGreaterThan(0);
    }

    //Método a probar: findAll()
    //Nombre de la prueba: shouldReturnMoreThanOneConstructorWhenSaveTwoConstructors()

    @Test
    void shouldReturnMoreThanOneConstructorWhenSaveTwoConstructors() {
        //Given
        // When
        constructorRepository.save(constructor);
        constructorRepository.save(constructor2);
        // Then
        assertThat(constructorRepository.findAll()).hasSizeGreaterThan(1);
    }

    // Método a probar: findByRefIgnoreCase()
    // Nombre de la prueba: shouldReturnConstructorNotNullWhenFindByRef()
    @Test
    void shouldReturnConstructorNotNullWhenFindByRef() {
        //Given
        constructorRepository.save(constructor);
        // When
        Constructor constructorByRef = constructorRepository.findByRefIgnoreCase("FER").get();
        // Then
        assertThat(constructorByRef).isNotNull();
    }

    // Método a probar: save() (Actualización)
    // Nombre de la prueba: shouldReturnConstructorNotNullWhenUpdateConstructor()

    @Test
    void shouldReturnConstructorNotNullWhenUpdateConstructor() {
        //Given
        constructorRepository.save(constructor);
        // When
        constructor.setName("Ferrari Racing");
        Constructor constructorUpdated = constructorRepository.save(constructor);
        // Then
        assertThat(constructorUpdated).isNotNull();
    }

    // Método a probar: deleteByRefIgnoreCase()
    // Nombre de la prueba: shouldReturnNullConstructorWhenDelete()
    @Test
    void shouldReturnNullDriverWhenDelete() {
        //Given
        constructorRepository.save(constructor);
        // When
        constructorRepository.deleteByRefIgnoreCase("FER");
        // Then
        assertThat(constructorRepository.findByRefIgnoreCase("FER")).isEmpty();
    }
}