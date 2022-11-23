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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
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
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    public static List<Hotel> hotelLista;
    public static Hotel hotel;

    @BeforeAll
    public static void cargarDatos(){
        List<Hotel> hotel = new CreacionObjetosFaker().fakerHotel(16);
    }

    @Test
    @DisplayName("Test 1 -> HotelService -> obtenerHoteldeUsuario(idUsuario)")
    void listarHotel() {
        when(hotelRepository.obtenerHoteldeUsuario(3)).thenReturn(hotelLista);
        List<Hotel> comprobarHotel = hotelService.obtenerHoteldeUsuario(3);
        assertNotNull(hotelService.obtenerHoteldeUsuario(3));
        assertEquals(hotelLista,comprobarHotel);
    }

    @Test
    @DisplayName("Test 2 -> HotelService -> findById()")
    void findById(){
        when(hotelRepository.findById(84)).thenReturn(Optional.ofNullable(hotel));
        Hotel hotelPrueba = hotelRepository.findById(84).orElse(null);
        assertEquals(hotelPrueba,hotel);
    }

//    @Test
//    @DisplayName("Test 3 -> HotelService -> guardarHotel(Hotel)")
//    void guardarHotel(){
//
//        Hotel hotelguardar = new Hotel();
//        nuevaHabitacion.setCapacidad(hotel.get(2).getCapacidad());
//
//        doAnswer(i->{
//            Habitacion arg0 = i.getArgument(0);
//            assertEquals(Habitacion.class,arg0.getClass());
//            assertEquals(nuevaHabitacion.getCapacidad(), arg0.getCapacidad());
//            assertNotNull(arg0);
//            return null;
//        }).when(habitacionRepository).save(any(Habitacion.class));
//
//        habitacionService.guardarHabitacion(habitaciones.get(2));
//    }

}


