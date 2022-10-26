package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Pension;
import com.example.booking.models.Tarifa;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.TarifaService;
import com.fasterxml.jackson.databind.BeanProperty;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@RequestMapping("/tarifa")
@Controller
public class TarifaController {
    @Autowired
    HabitacionRepository habitacionRepository;
    @Autowired
    TarifaService tarifaService;



    @GetMapping("/generar")
    public void generarhabitacionesAleatorio() {

        Faker faker = new Faker();

        for (Habitacion h : habitacionRepository.obtenertodaslashabitaciones()) {
            Tarifa tarifa = new Tarifa();
            if (h.isWifi()) {
                tarifa.setPrecioWifi(faker.number().randomDouble(1, 5, 20));
            }else{
                tarifa.setPrecioWifi(0.0);
            }
            if (h.isCajaFuerte()) {
                tarifa.setPrecioCajaFuerte(faker.number().randomDouble(1, 5, 20));
            }else{
                tarifa.setPrecioCajaFuerte(0.0);
            }
            if (h.isCocina()) {
                tarifa.setPrecioCocina(faker.number().randomDouble(1, 5, 20));
            }else{
                tarifa.setPrecioCocina(0.0);
            }
            if (h.isAireAcondicionado()){
                tarifa.setPrecioAire(faker.number().randomDouble(1, 5, 20));
            }else{
                tarifa.setPrecioAire(0.0);
            }
            if (h.isTv()) {
                tarifa.setPrecioTV(faker.number().randomDouble(1, 5, 20));
            }else{
                tarifa.setPrecioTV(0.0);
            }
            if (h.isTerraza()) {
                tarifa.setPrecioTerraza(faker.number().randomDouble(1, 5, 20));
            }else{
                tarifa.setPrecioTerraza(0.0);
            }
            if (h.isBanioPrivado()) {
                tarifa.setPrecioBanio(faker.number().randomDouble(1, 5, 20));
            }else{
                tarifa.setPrecioBanio(0.0);
            }
            tarifa.setHotelTarifa(h.getHotel());
            tarifaService.guardarTarifa(tarifa);
}
    }
}
