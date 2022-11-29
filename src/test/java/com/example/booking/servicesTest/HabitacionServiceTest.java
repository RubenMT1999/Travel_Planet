package com.example.booking.servicesTest;

import com.example.booking.CreacionObjetosFaker;
import com.example.booking.models.Habitacion;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.IPageRepositoryHab;
import com.example.booking.repository.PageRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    @Mock
    private IPageRepositoryHab pageRepositoryHab;

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
                habitaciones.get(1).isWifi());
    }

    @Test
    @DisplayName("Test 6 -> HabitacionService -> habitacionFiltroPrecio")
    void habitacionfiltroprecio() {

        when(habitacionRepository.buscarfiltrosidprecio(any(Integer.class),any(Date.class),any(Date.class),any(Integer.class),
                anyList(),anyList(),anyList(),anyList(),anyList(),anyList(),anyList(),
                any(Double.class),any(Integer.class),any(Integer.class),any(Double.class))).thenReturn(habitaciones);

        List<Habitacion> habitacionesPrueba = habitacionService.habitacionfiltroprecio(habitaciones.get(1).getId(), new Date(),
                new Date(), habitaciones.get(1).getCapacidad(),habitaciones.get(1).isWifi(),
                habitaciones.get(1).isTerraza(), habitaciones.get(1).isTv(), habitaciones.get(1).isAireAcondicionado(),
                habitaciones.get(1).isBanioPrivado(), habitaciones.get(1).isCocina(), habitaciones.get(1).isCajaFuerte(),
                habitaciones.get(1).getPrecioBase(), 5);

        //Verifica que se ha ejecutado una vez el repositorio
        verify(habitacionRepository, times(1)).buscarfiltrosidprecio(any(Integer.class),any(Date.class),any(Date.class),any(Integer.class),
                anyList(),anyList(),anyList(),anyList(),anyList(),anyList(),anyList(),
                any(Double.class),any(Integer.class),any(Integer.class),any(Double.class));
        assertEquals(habitacionesPrueba,habitaciones);
    }


    @Test
    @DisplayName("Test 7 -> HabitacionService -> buscarHabitaciones")
    void buscarHabitaciones() throws ParseException {

        when(habitacionRepository.buscarHabitacionesrepo(any(Integer.class),any(Integer.class),any(Date.class),any(Date.class))).thenReturn(habitaciones);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_inicio = formato.parse("2011-11-11");
        Date fecha_fin = formato.parse("2012-11-11");

        List<Habitacion> habitacionesPrueba = habitacionService.buscarHabitaciones(2,3,fecha_inicio,fecha_fin);

        verify(habitacionRepository,times(1)).buscarHabitacionesrepo(any(Integer.class),any(Integer.class),any(Date.class),any(Date.class));
        assertEquals(habitaciones,habitacionesPrueba);
    }


    @Test
    @DisplayName("Test 8 -> HabitacionService -> listarHabitacionesPages")
    void listarHabitacionesPages(){

        Page<Habitacion> paginador = new PageImpl<>(habitaciones);

        when(pageRepositoryHab.listarHabitacionesPages(any(Pageable.class),any(Integer.class))).thenReturn(paginador);

        Pageable pageRequest = PageRequest.of(1, 5);
        Page<Habitacion> paginadorPrueba = habitacionService.listarHabitacionesPages(pageRequest,8);

        verify(pageRepositoryHab,times(1)).listarHabitacionesPages(any(Pageable.class),any(Integer.class));
        assertEquals(paginador,paginadorPrueba);
    }

    @Test
    @DisplayName("Test 9 -> HabitacionService - buscarHabitacionesPages")
    void buscarHabitacionesPages() throws ParseException {

        Page<Habitacion> paginador = new PageImpl<>(habitaciones);

        when(pageRepositoryHab.buscarHabitacionesPages(any(Integer.class),any(Integer.class),any(Date.class),any(Date.class),any(Pageable.class)))
                .thenReturn(paginador);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_inicio = formato.parse("2011-11-11");
        Date fecha_fin = formato.parse("2012-11-11");
        Pageable pageRequest = PageRequest.of(1, 5);

        Page<Habitacion> paginadorPrueba = habitacionService.buscarHabitacionesPages(8,6,fecha_inicio,fecha_fin,pageRequest);

        verify(pageRepositoryHab,times(1)).buscarHabitacionesPages(any(Integer.class),any(Integer.class),any(Date.class)
                                                                ,any(Date.class),any(Pageable.class));
        assertEquals(paginador,paginadorPrueba);

    }

    @Test
    @DisplayName("Test 10 -> HabitacionService -> comprobarNumHab")
    void comprobarNumHab(){

        when(habitacionRepository.comprobarNumHab(any(Integer.class),any(Integer.class))).thenReturn(8);

        Integer numeroPrueba = habitacionService.comprobarNumHab(65,24);

        verify(habitacionRepository,times(1)).comprobarNumHab(any(Integer.class),any(Integer.class));
        assertEquals(numeroPrueba,8);
    }


    @Test
    @DisplayName("Test 11 -> HabitacionService -> editarDisponibilidad")
    void editarDisponibilidad(){

        doAnswer(i->{
            Boolean arg0 = i.getArgument(0);
            Integer arg1 = i.getArgument(1);
            verify(habitacionRepository, times(1)).editarDisponibilidad(arg0,arg1);
            return null;
        }).when(habitacionRepository).editarDisponibilidad(any(boolean.class),any(Integer.class));

        habitacionService.editarDisponibilidad(true,8);
    }


    @Test
    @DisplayName("Test 12 -> HabitacionService -> obtenerHabitacionReserva")
    void obtenerHabitacionReserva(){

        when(habitacionRepository.obtenerHabitacionReserva(any(Integer.class))).thenReturn(habitaciones.get(0));

        Habitacion habitacionPrueba = habitacionService.obtenerHabitacionReserva(9);

        verify(habitacionRepository,times(1)).obtenerHabitacionReserva(any(Integer.class));
        assertEquals(habitacionPrueba,habitaciones.get(0));
    }



}