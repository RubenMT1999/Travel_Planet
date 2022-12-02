package com.example.booking.servicesTest;


import com.example.booking.CreacionObjetosFaker;
import com.example.booking.models.Pago;
import com.example.booking.repository.PagoRepository;
import com.example.booking.services.PagoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;


    @InjectMocks
    private PagoService pagoService;

    public static List<Pago> pagos;


    @BeforeAll
    public static void cargarDatos(){
        pagos = new CreacionObjetosFaker().fakerPago(6);
    }

    @Test
    @DisplayName("Test 1 -> PagosService -> Crear Pago" )
    void crearPago(){
        pagoService.guardarPago(pagos.get(3));

        verify(pagoRepository, times(1)).save(pagos.get(3));
    }








}
