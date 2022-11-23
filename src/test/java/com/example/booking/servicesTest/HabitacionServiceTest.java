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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

//        Habitacion nuevaHabitacion = new Habitacion();
//        nuevaHabitacion.setCapacidad(habitaciones.get(2).getCapacidad());
//
//        doAnswer(i->{
//            Habitacion arg0 = i.getArgument(0);
//            assertEquals(nuevaHabitacion.getCapacidad(), arg0.getCapacidad());
//            verify(habitacionRepository, times(1)).save(arg0);
//            assertNotNull(arg0);
//            return null;
//        }).when(habitacionRepository).save(any(Habitacion.class));
//
        habitacionService.guardarHabitacion(habitaciones.get(2));

        verify(habitacionRepository, times(1)).save(habitaciones.get(2));
    }

    @Test
    @DisplayName("Test 4 -> HabitacionService -> borrarHabitacion(Habitacion)")
    void borrarHabitacion(){

        doAnswer(i->{
            Habitacion arg0 = i.getArgument(0);
            verify(habitacionRepository, times(1)).delete(arg0);
            return null;
        }).when(habitacionRepository).delete(any(Habitacion.class));

        habitacionService.borrarHabitacion(habitaciones.get(1));
    }

    @Test
    @DisplayName("Test 5 -> HabitacionService -> guardarPersonalizado")
    void guardarPersonalizado(){

        habitacionService.guardarPersonalizado(habitaciones.get(1).getHotel().getId(),habitaciones.get(1).getNumeroHabitacion(),habitaciones.get(1).getExtensionTelefonica(),
                habitaciones.get(1).getCapacidad(),habitaciones.get(1).getImagen(),habitaciones.get(1).getDescripcion(),habitaciones.get(1).getPrecioBase(),habitaciones.get(1).isCajaFuerte(),
                habitaciones.get(1).isCocina(),habitaciones.get(1).isBanioPrivado(),habitaciones.get(1).isAireAcondicionado(),habitaciones.get(1).isTv(),habitaciones.get(1).isTerraza(),
                habitaciones.get(1).isWifi());


        verify(habitacionRepository, times(1)).guardarPersonalizado(
                habitaciones.get(1).getHotel().getId(),habitaciones.get(1).getNumeroHabitacion(),habitaciones.get(1).getExtensionTelefonica(),
                habitaciones.get(1).getCapacidad(),habitaciones.get(1).getImagen(),habitaciones.get(1).getDescripcion(),habitaciones.get(1).getPrecioBase(),habitaciones.get(1).isCajaFuerte(),
                habitaciones.get(1).isCocina(),habitaciones.get(1).isBanioPrivado(),habitaciones.get(1).isAireAcondicionado(),habitaciones.get(1).isTv(),habitaciones.get(1).isTerraza(),
                habitaciones.get(1).isWifi()
        );
    }

    @Test
    @DisplayName("Test 6 -> HabitacionService -> habitacionFiltroPrecio")
    void habitacionfiltroprecio(){

        when(habitacionRepository.buscarfiltrosidprecio(any(Integer.class,Date.class,Date.class,)))

        habitacionService.habitacionfiltroprecio(habitaciones.get(1).getId(), new Date(),
                new Date(), habitaciones.get(1).getCapacidad(),habitaciones.get(1).isWifi(),
                habitaciones.get(1).isTerraza(), habitaciones.get(1).isTv(), habitaciones.get(1).isAireAcondicionado(),
                habitaciones.get(1).isBanioPrivado(), habitaciones.get(1).isCocina(), habitaciones.get(1).isCajaFuerte(),
                habitaciones.get(1).getPrecioBase(), 5);

        verify(habitacionRepository, times(1)).buscarfiltrosidprecio()

    }

}