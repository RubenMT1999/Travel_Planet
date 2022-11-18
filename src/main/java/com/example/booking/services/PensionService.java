package com.example.booking.services;

import com.example.booking.models.EPension;
import com.example.booking.models.Tarifa;
import com.example.booking.models.Temporada;
import com.example.booking.repository.IPensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PensionService {

    @Autowired
    IPensionRepository pensionRepository;


    public Double precioPension(EPension pension, Tarifa tarifa){
        return pensionRepository.precioPension(pension,tarifa);
    }

    public Double precioTemporada(Temporada temporada, Tarifa tarifa){
        return pensionRepository.precioTemporada(temporada, tarifa);
    }


}
