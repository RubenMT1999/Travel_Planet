package com.example.booking.servicesTest;

import com.example.booking.CreacionObjetosFaker;
import com.example.booking.models.EPension;
import com.example.booking.models.PensionHotel;
import com.example.booking.models.Tarifa;
import com.example.booking.models.Temporada;
import com.example.booking.repository.TarifaRepository;
import com.example.booking.services.TarifaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TarifaServiceTest {

    @Mock
    TarifaRepository tarifaRepository;

    @InjectMocks
    TarifaService tarifaService;

    public static List<Tarifa> misTarifas;

    @BeforeAll
    public static void cargarDatosFaker(){
        misTarifas = new CreacionObjetosFaker().fakerTarifa(10);
    }


    @Test
    @DisplayName("Test 1 -> TarifaService -> guardarTarifa()")
    void guardarTarifa(){
        doAnswer(i->{
            Integer idHotel = i.getArgument(7);
            verify(tarifaRepository,times(1)).guardarTarifa(2.00,2.00,
                                                                        2.00,2.00,2.00,
                                                                        2.00,2.00,6);
            assertEquals(idHotel,6);
            return null;
        }).when(tarifaRepository).guardarTarifa(any(Double.class),any(Double.class),any(Double.class),any(Double.class),
                any(Double.class),any(Double.class),any(Double.class),any(Integer.class));

        tarifaService.guardarTarifa(2.00,2.00,2.00,2.00,2.00,2.00,2.00,6);
    }


    @Test
    @DisplayName("Test 2 -> TarifaService -> tarifaEditar()")
    void tarifaEditar(){
        when(tarifaRepository.save(any(Tarifa.class))).thenReturn(misTarifas.get(4));

        Tarifa tarifaPrueba = tarifaService.tarifaEditar(misTarifas.get(0));

        verify(tarifaRepository,times(1)).save(misTarifas.get(0));
        assertEquals(tarifaPrueba,misTarifas.get(4));
    }



    @Test
    @DisplayName("Test 3 -> TarifaService -> verTarifa()")
    void verTarifa(){
        when(tarifaRepository.listarTarifa(any(Integer.class))).thenReturn(misTarifas.get(4));

        Tarifa tarifaPrueba = tarifaService.verTarifa(2);

        verify(tarifaRepository,times(1)).listarTarifa(2);
        assertEquals(tarifaPrueba,misTarifas.get(4));
    }


    @Test
    @DisplayName("Test 4 -> TarifaService -> guardarPension()")
    void guardarPension(){
        doAnswer(i->{
            verify(tarifaRepository,times(1)).guardarPension(3,8.50,7);
            assertNotNull(i.getArgument(0));
            assertNotNull(i.getArgument(1));
            assertNotNull(i.getArgument(2));
            return null;
        }).when(tarifaRepository).guardarPension(any(Integer.class),any(Double.class),any(Integer.class));

        tarifaService.guardarPension(3,8.50,7);
    }


//    @Test
//    @DisplayName("Test 5 -> TarifaService -> modificarPension()")
//    void modificarPension(){
//        doAnswer(i->{
//            verify(tarifaRepository,times(1)).modificarPension(3.00,8,7);
//            assertNotNull(i.getArgument(0));
//            assertNotNull(i.getArgument(1));
//            assertNotNull(i.getArgument(2));
//            return null;
//        }).when(tarifaRepository).modificarPension(any(Double.class),any(Integer.class),any(Integer.class));
//
//        tarifaService.modificarPension(3.00,8,7);
//
//    }

}
