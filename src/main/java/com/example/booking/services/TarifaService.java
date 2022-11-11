package com.example.booking.services;

import com.example.booking.models.Tarifa;
import com.example.booking.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.DoubleBuffer;

@Service
public class TarifaService {

    @Autowired
    TarifaRepository tarifaRepository;

    public Tarifa tarifaNuevaId(int id){
        return tarifaRepository.findById(id).orElse(null);
    }

    public Tarifa tarifaNueva(Tarifa tarifa){return tarifaRepository.save(tarifa);}

    public void guardarTarifa(Double precioBanio, Double precioCajaFuerte, Double precioCocina, Double precioTv, Double precioTerraza, Double precioWifi, Double precioAire, Integer id_hotel){
        tarifaRepository.guardarTarifa(precioBanio,precioCajaFuerte,precioCocina,precioTv,precioTerraza,precioWifi,precioAire,id_hotel);
    }



}
