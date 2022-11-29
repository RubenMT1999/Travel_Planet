package com.example.booking.controllers;

import com.example.booking.models.*;
import com.example.booking.repository.IPensionRepository;
import com.example.booking.repository.TarifaRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PensionController {
    @Autowired
    private TarifaRepository tarifaRepository;
    @Autowired
    private IPensionRepository iPensionRepository;


    @GetMapping("/generarPension")
    public void generarhabitacionesAleatorio(){

        Faker faker = new Faker();
        List<Tarifa> listaTarifa = tarifaRepository.findAll();
        Integer contador = 1;
        for (Tarifa t : listaTarifa) {
            if (contador == 1) {
                PensionHotel pensionHotel = new PensionHotel();
                pensionHotel.setPension(EPension.AlojamientoDesayuno);
                pensionHotel.setPrecio(faker.number().randomDouble(1, 5, 15));
                pensionHotel.setTarifa(t);

                iPensionRepository.save(pensionHotel);
                contador++;
            }
            if (contador == 2) {
                PensionHotel pensionHotel = new PensionHotel();
                pensionHotel.setPension(EPension.MediaPension);
                pensionHotel.setPrecio(faker.number().randomDouble(1, 15, 20));
                pensionHotel.setTarifa(t);
                iPensionRepository.save(pensionHotel);
                contador++;
            }
            if (contador == 3) {
                PensionHotel pensionHotel = new PensionHotel();
                pensionHotel.setPension(EPension.MediaPension);
                pensionHotel.setPrecio(faker.number().randomDouble(1, 20, 25));
                pensionHotel.setTarifa(t);
                iPensionRepository.save(pensionHotel);
                contador++;
            }
            if (contador == 4) {
                PensionHotel pensionHotel = new PensionHotel();
                pensionHotel.setPension(EPension.TodoIncluido);
                pensionHotel.setPrecio(faker.number().randomDouble(1, 25, 35));
                pensionHotel.setTarifa(t);
                iPensionRepository.save(pensionHotel);
                contador = 1;
            }


        }

    }
}
