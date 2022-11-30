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

    public static List<Hotel> hotel;


    @BeforeAll
    public static void cargarDatos(){
         hotel = new CreacionObjetosFaker().fakerHotel(16, 3);
    }



    @Test
    @DisplayName("Test 1 -> HotelService -> obtenerHoteldeUsuario(idUsuario)")
    void listarHotel() {
        when(hotelRepository.obtenerHoteldeUsuario(3)).thenReturn(hotel);
        List<Hotel> comprobarHotel = hotelService.obtenerHoteldeUsuario(3);
        assertNotNull(hotelService.obtenerHoteldeUsuario(3));
        assertEquals(hotel,comprobarHotel);
    }

    @Test
    @DisplayName("Test 2 -> HotelService -> findById()")
    void findById(){
        when(hotelRepository.findById(10)).thenReturn(Optional.ofNullable(hotel.get(0)));
        Hotel hotelPrueba = hotelService.findById(10);
        assertEquals(hotelPrueba,hotel.get(0));
    }

    @Test
    @DisplayName("Test 3 -> HotelService -> guardarHotel(Hotel)")
    void guardarHotel(){
        Hotel hotelguardar = new Hotel();
        hotelguardar.setLugar(hotel.get(2).getLugar());
        doAnswer(i->{
            Hotel arg0 = i.getArgument(0);
            assertEquals(Hotel.class,arg0.getClass());
            assertEquals(hotelguardar.getLugar(), arg0.getLugar());
            assertNotNull(arg0);
            return null;
        }).when(hotelRepository).save(any(Hotel.class));
        hotelService.guardarHotel(hotel.get(2));
    }
    @Test
    @DisplayName("Test 4 -> HotelService -> borrarHotel(Hotel)")
    void borrarHotel(){
        when(hotelRepository.findById(5)).thenReturn(Optional.ofNullable(hotel.get(0)));
        Hotel buscar = hotelService.findById(5);
        hotelService.hotelEliminar(16);
        Hotel hotelPrueba = hotelService.findById(16);
        assertNull(hotelPrueba);
    }

}







