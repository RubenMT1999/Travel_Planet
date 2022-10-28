package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Pension;
import com.example.booking.models.Tarifa;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.HotelRepository;
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
    HotelRepository hotelRepository;
    @Autowired
    TarifaService tarifaService;


    //Faker para generar tarifas aleatorios.

   /* @GetMapping("/generar")
    public void generarhabitacionesAleatorio() {

        Faker faker = new Faker();

        for (Hotel h : hotelRepository.obtenerTodoshoteles()) {
            Tarifa tarifa = new Tarifa();
                tarifa.setPrecioWifi(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioCajaFuerte(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioCocina(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioAire(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioTV(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioTerraza(faker.number().randomDouble(1, 5, 20));
                tarifa.setPrecioBanio(faker.number().randomDouble(1, 5, 20));
            tarifa.setHotelTarifa(h);
            tarifaService.guardarTarifa(tarifa);
}
    }

    */
}
