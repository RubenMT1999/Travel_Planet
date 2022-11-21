package com.example.booking.controllers;

import com.example.booking.models.Tarifa;
import com.example.booking.models.Temporada;
import com.example.booking.models.TemporadaHotel;
import com.example.booking.repository.TarifaRepository;
import com.example.booking.repository.TemporadaRepository;
import com.example.booking.services.TarifaService;
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
public class TemporadaController {
    @Autowired
    private TarifaRepository tarifaRepository;
    @Autowired
    private TemporadaRepository temporadaRepository;

    @GetMapping("/generarTemporada")
    public void generarhabitacionesAleatorio() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String bajaIn1 = "2022-11-01";
        String bajaFin1 = "2023-03-31";
        Date bajaIn = formato.parse(bajaIn1);
        Date bajaFin = formato.parse(bajaFin1);

        String mediaIn1 = "2023-04-01";
        String mediaFin1 = "2023-06-30";
        Date mediaIn = formato.parse(mediaIn1);
        Date mediaFin = formato.parse(mediaFin1);

        String altaIn1 = "2023-07-01";
        String altaFin1 = "2023-09-30";
        Date altaIn = formato.parse(altaIn1);
        Date altaFin = formato.parse(altaFin1);
        Faker faker = new Faker();
        List<Tarifa> listaTarifa = tarifaRepository.findAll();
        Integer contador = 1;
        for (Tarifa t : listaTarifa) {
            List<TemporadaHotel> temporadas = new ArrayList<>();
            if(contador == 1){
                TemporadaHotel temporada = new TemporadaHotel();
                temporada.setFechaInicio(bajaIn);
                temporada.setFechaFin(bajaFin);
                temporada.setPrecio(faker.number().randomDouble(1, 5, 15));
                temporada.setTemporada(Temporada.Baja);
                temporada.setTarifa(t);
                temporadaRepository.save(temporada);
                contador++;
            }
            if(contador == 2){
                TemporadaHotel temporada = new TemporadaHotel();
                temporada.setFechaInicio(mediaIn);
                temporada.setFechaFin(mediaFin);
                temporada.setPrecio(faker.number().randomDouble(1, 15,30));
                temporada.setTemporada(Temporada.Media);
                temporada.setTarifa(t);
                temporadaRepository.save(temporada);
                contador++;
            }
            if(contador == 3){
                TemporadaHotel temporada = new TemporadaHotel();
                temporada.setFechaInicio(altaIn);
                temporada.setFechaFin(altaFin);
                temporada.setPrecio(faker.number().randomDouble(1, 25, 40));
                temporada.setTemporada(Temporada.Alta);
                temporada.setTarifa(t);
                temporadaRepository.save(temporada);
                contador = 1;
            }

        }

    }
}
