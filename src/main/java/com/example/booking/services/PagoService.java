package com.example.booking.services;

import com.example.booking.models.Pago;
import com.example.booking.models.Reserva;
import com.example.booking.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public Pago guardarPago(Pago pago){
        return pagoRepository.save(pago);
    }

}
