package com.example.booking.servicesTest;


import com.example.booking.CreacionObjetosFaker;
import com.example.booking.controllers.PensionController;
import com.example.booking.models.EPension;
import com.example.booking.models.PensionHotel;
import com.example.booking.models.Tarifa;
import com.example.booking.models.Temporada;
import com.example.booking.repository.IPensionRepository;
import com.example.booking.repository.PagoRepository;
import com.example.booking.services.PensionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PensionServiceTest {

    @Mock
    private IPensionRepository iPensionRepository;


    @InjectMocks
    private PensionService pensionService;

    public static List<PensionHotel> pensionHotels;

    @BeforeAll
    public static void cargarDatos(){pensionHotels = new CreacionObjetosFaker().fakerPension(7);}


    @Test
    @DisplayName("Test 1 -> PensionService -> Precio Pension" )
    void precioPension(){
        doAnswer(i->{

                    EPension arg0 = i.getArgument(0);
                    Tarifa arg1 =  i.getArgument(1);

                    verify(iPensionRepository,times(1)).precioPension(arg0,arg1);
                    return null;
                }).when(iPensionRepository).precioPension(any(EPension.class),any(Tarifa.class));

        Tarifa tarifa = new Tarifa();

        iPensionRepository.precioPension(EPension.TodoIncluido,tarifa);


    }

    @Test
    @DisplayName("Test 2 -> PensionService -> Precio Temporada" )
    void precioTemporada(){
        doAnswer(i->{

            Temporada arg0 = i.getArgument(0);
            Tarifa arg1 =  i.getArgument(1);

            verify(iPensionRepository,times(1)).precioTemporada(arg0,arg1);
            return null;
        }).when(iPensionRepository).precioTemporada(any(Temporada.class),any(Tarifa.class));

        Tarifa tarifa = new Tarifa();

        iPensionRepository.precioTemporada(Temporada.Alta,tarifa);


    }

}
