package com.example.booking.servicesTest;

import com.example.booking.CreacionObjetosFaker;
import com.example.booking.models.Habitacion;
import com.example.booking.models.*;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@DataJpaTest
//@TestPropertySource(properties = {
//        "spring.jpa.hibernate.ddl-auto=validate",
//        "spring.datasource.driver-class-name=org.mariadb.jdbc.Driver",
//        "spring.datasource.url=jdbc:h2:mem:db",
//        "spring.jpa.properties.hibernate.default_schema=",
//        "spring.jpa.hibernate.ddl-auto=update"
//})
class BuscadorServiceTest {
    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    public static List<Hotel> hotelbusqueda;


    @BeforeAll
    public static void cargarDatos() throws ParseException {

        hotelbusqueda = new CreacionObjetosFaker().fakerHotelBusqueda();
    }

    @Test
    @DisplayName("Test 1 -> HotelService -> obtenerHotelBusqueda(Ciudad, Fecha inicio, Fecha Fin, Capacidad)")
    void listarHotelBusqueda() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = formato.parse("2022-12-1");
        Date fecha_Fin = formato.parse("2022-12-12");
        Date inicio = formato.parse("2020-11-30");
        Date fin = formato.parse("2020-12-11");
        when(hotelRepository.buscadortest("Sevilla", fechaInicio, fecha_Fin, 2)).thenReturn(hotelbusqueda);
        List<Hotel> busqueda = hotelService.buscartest("Sevilla", fechaInicio, fecha_Fin, 2);
        List<Hotel> busquedanull = hotelService.buscartest("Sevilla", inicio, fin, 2);
        assertEquals(busqueda.size(), 3);
        assertEquals(busquedanull.size(), 0);
    }

}
