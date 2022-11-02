package com.example.booking.services;

import com.example.booking.models.Tarifa;
import com.example.booking.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarifaService {

    @Autowired
    TarifaRepository tarifaRepository;

    public Tarifa tarifaNuevaId(int id){
        return tarifaRepository.findById(id).orElse(null);
    }

    public Tarifa tarifaNueva(Tarifa tarifa){return tarifaRepository.save(tarifa);}


}
