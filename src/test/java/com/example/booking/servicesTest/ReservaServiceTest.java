package com.example.booking.servicesTest;

import com.example.booking.CreacionObjetosFaker;
import com.example.booking.models.Reserva;
import com.example.booking.repository.ReservaRepository;
import com.example.booking.services.ReservaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    ReservaRepository reservaRepository;

    @InjectMocks
    ReservaService reservaService;

    public static List<Reserva> misReservas;

    @BeforeAll
    public static void cargarDatosFaker(){
        misReservas = new CreacionObjetosFaker().fakerReservas(10);
    }


    @Test
    @DisplayName("Test 1 -> ReservaService -> listar()")
    void listar(){
        when(reservaRepository.findAll()).thenReturn(misReservas);

        List<Reserva> reservasPrueba = reservaService.listar();

        verify(reservaRepository,times(1)).findAll();
        assertEquals(reservasPrueba,misReservas);
        assertNotNull(reservasPrueba);
    }

    @Test
    @DisplayName("Test 2 -> ReservaService -> reservasPorNombre()")
    void reservasPorNombre(){
        when(reservaRepository.reservasPorNombre(any(String.class))).thenReturn(misReservas);

        List<Reserva> reservasPrueba = reservaService.reservasPorNombre("Ruben");

        verify(reservaRepository,times(1)).reservasPorNombre(any(String.class));
        assertEquals(reservasPrueba,misReservas);
        assertNotNull(reservasPrueba);
    }


    @Test
    @DisplayName("Test 3 -> ReservaService -> guardarReserva()")
    void guardarReserva(){

        doAnswer(i->{
            Reserva reserva = i.getArgument(0);
            verify(reservaRepository,times(1)).save(reserva);
            assertNotNull(reserva);
            return null;
        }).when(reservaRepository).save(any(Reserva.class));

        reservaService.guardarReserva(misReservas.get(0));
    }


    @Test
    @DisplayName("Test 4 -> ReservaService -> obtenerReservaUsuario()")
    void obtenerReservaUsuario(){
        when(reservaRepository.obtenerReserva(any(Integer.class))).thenReturn(misReservas);

        List<Reserva> reservasPrueba = reservaService.obtenerReservaUsuario(7);

        verify(reservaRepository,times(1)).obtenerReserva(7);
        assertEquals(misReservas,reservasPrueba);
        assertNotNull(reservasPrueba);
    }



    @Test
    @DisplayName("Test 5 -> ReservaService -> obtenerDetallesReserva()")
    void obtenerDetallesReserva(){
        when(reservaRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(misReservas.get(4)));

        Reserva reservaPrueba = reservaService.obtenerDetallesReserva(9);

        verify(reservaRepository,times(1)).findById(9);
        assertEquals(misReservas.get(4),reservaPrueba);
        assertNotNull(reservaPrueba);
    }


    @Test
    @DisplayName("Test 6 -> ReservaService -> cancelarReserva()")
    void cancelarReserva(){
        doAnswer(i->{
            Reserva reserva = i.getArgument(0);
            verify(reservaRepository,times(1)).delete(reserva);
            assertNotNull(reserva);
            return null;
        }).when(reservaRepository).delete(any(Reserva.class));

        reservaService.cancelarReserva(misReservas.get(3));
    }


    @Test
    @DisplayName("Test 7 -> ReservaService -> editarPagado()")
    void editarPagado(){
        doAnswer(i->{
            Integer idHabitacion = i.getArgument(1);
            verify(reservaRepository,times(1)).editarPagado(true,5);
            assertNotNull(idHabitacion);
            return null;
        }).when(reservaRepository).editarPagado(any(Boolean.class),any(Integer.class));

        reservaService.editarPagado(true,5);
    }




}
