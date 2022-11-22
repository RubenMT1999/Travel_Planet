package com.example.booking.servicesTest;

import com.example.booking.CreacionObjetosFaker;
import com.example.booking.models.Habitacion;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.services.HabitacionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.datasource.driver-class-name=org.mariadb.jdbc.Driver",
        "spring.datasource.url=jdbc:h2:mem:db",
        "spring.jpa.properties.hibernate.default_schema=",
        "spring.jpa.hibernate.ddl-auto=update"
})
class HabitacionServiceTest {
    @Mock
    private HabitacionRepository habitacionRepository;

    @InjectMocks
    private HabitacionService habitacionService;

    public static List<Habitacion> habitaciones;

    @BeforeAll
    public static void cargarDatos(){
        habitaciones = new CreacionObjetosFaker().fakerHabitaciones(10,16);
    }

    @Test
    @DisplayName("Test 1 -> HabitacionesService -> listarHabitaciones(idHotel)")
    void listarHabitaciones() {
        when(habitacionRepository.listarHabitaciones(16)).thenReturn(habitaciones);

        List<Habitacion> comprobarHabitaciones = habitacionService.listarHabitaciones(16);

        assertNotNull(habitacionService.listarHabitaciones(16));
        assertEquals(habitaciones,comprobarHabitaciones);
    }


    @Test
    @DisplayName("Test 2 -> HabitacionesService -> findById()")
    void findById(){
        when(habitacionRepository.findById(84)).thenReturn(Optional.ofNullable(habitaciones.get(0)));

        Habitacion habitacionPrueba = habitacionService.findById(84);

        assertEquals(habitacionPrueba,habitaciones.get(0));
    }


    @Test
    @DisplayName("Test 3 -> HabitacionesService -> guardarHabitacion(Habitacion)")
    void guardarHabitacion(){
        when(habitacionRepository.save(Mockito.any(Habitacion.class))).thenReturn(habitaciones.get(0));

        Habitacion miHabitacion = habitacionRepository.save(habitaciones.get(2));
        asserThat(returnedStudent, hasProperty("firstName", equalTo("John")));

    }







}