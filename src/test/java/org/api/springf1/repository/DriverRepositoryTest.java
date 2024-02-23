package org.api.springf1.repository;

import org.api.springf1.dto.DriverDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.api.springf1.model.Driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class DriverRepositoryTest {

    @Autowired
    DriverRepository driverRepository;

    Driver driver;
    Driver driver2;

    @BeforeEach
    void setUp() {
        driver = Driver.builder().id(1L).code("HAM").forename("Lewis").surname("Hamilton").build();
        driver2 = Driver.builder().id(2L).code("BOT").forename("Valtteri").surname("Bottas").build();
    }


    @Test
    void shouldReturnDriverWhenSave() {
        //Given
        // When
        Driver driverSaved = driverRepository.save(driver);
        // Then
        assertThat(driverSaved).isNotNull();
        assertThat(driverSaved.getId()).isGreaterThan(0);
    }

    //Método a probar: findAll()
    //Nombre de la prueba: shouldReturnMoreThanOneDriverWhenSaveTwoDrivers()

    @Test
    void shouldReturnMoreThanOneDriverWhenSaveTwoDrivers() {
        //Given
        // When
        driverRepository.save(driver);
        driverRepository.save(driver2);
        // Then
        assertThat(driverRepository.findAll()).hasSizeGreaterThan(1);
    }

    // Método a probar: findByCodeIgnoreCase()
    // Nombre de la prueba: shouldReturnDriverNotNullWhenFindByCode()
    @Test
    void shouldReturnDriverNotNullWhenFindByCode() {
        //Given
        driverRepository.save(driver);
        // When
        Driver driverByCode = driverRepository.findByCodeIgnoreCase("HAM").get();
        // Then
        assertThat(driverByCode).isNotNull();
    }

    // Método a probar: save() (Actualización)
    // Nombre de la prueba: shouldReturnDriverNotNullWhenUpdateDriver()

    @Test
    void shouldReturnDriverNotNullWhenUpdateDriver() {
        //Given
        driverRepository.save(driver);
        // When
        driver.setForename("Luis");
        driver.setSurname("Hamilton");
        Driver driverUpdated = driverRepository.save(driver);
        // Then
        assertThat(driverUpdated).isNotNull();
    }

    // Método a probar: deleteByCode()
    // Nombre de la prueba: shouldReturnNullDriverWhenDelete()
    @Test
    void shouldReturnNullDriverWhenDelete() {
        //Given
        driverRepository.save(driver);
        // When
        driverRepository.deleteByCodeIgnoreCase("HAM");
        // Then
        assertThat(driverRepository.findByCodeIgnoreCase("HAM")).isEmpty();
    }
}