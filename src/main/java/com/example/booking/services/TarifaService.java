package com.example.booking.services;

import com.example.booking.models.PensionHotel;
import com.example.booking.models.Tarifa;
import com.example.booking.models.TemporadaHotel;
import com.example.booking.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.DoubleBuffer;
import java.util.Date;

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

    public Tarifa buscarId (Integer id){
        return tarifaRepository.findById(id).orElse(null);
    }
    public Tarifa tarifaEditar (Tarifa tarifa){
        return tarifaRepository.save(tarifa);
    }

    public Tarifa verTarifa (Integer id){
        return tarifaRepository.listarTarifa(id);
    }

    public void guardarPension (Integer tipoPension, Double precio, Integer idTarifa){
        tarifaRepository.guardarPension(tipoPension,precio,idTarifa);
    }

    public void modificarPension(Double precio, Integer idTarifa, Integer tipoPension){
        tarifaRepository.modificarPension(precio,idTarifa,tipoPension);
    }
    public void guardarTemporada(Integer tipoTemporada, Date fechaInicio, Date fechaFin, Double precio, Integer idTarifa){
        tarifaRepository.guardarTemporada(tipoTemporada,fechaInicio,fechaFin,precio,idTarifa);
    }

    public void modificarTemporada(Date fechaInicio, Date fechaFin, Double precio, Integer idTarifa, Integer tipoTemporada){
        tarifaRepository.modificarTemporada(fechaInicio,fechaFin,precio,idTarifa,tipoTemporada);
    }

    public PensionHotel verPension (Integer id_tarifa){
        return tarifaRepository.listarPension(id_tarifa);
    }

    public TemporadaHotel verTemporada (Integer id_tarifa){
        return tarifaRepository.listarTemporada(id_tarifa);
    }

}
