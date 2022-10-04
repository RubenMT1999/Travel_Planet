package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.repository.RegistroHotelRepository;
import com.example.booking.services.HotelRegistroService;
import com.example.booking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registro-hotel")
public class RegristroHotelController {

    @Autowired
    private  HotelRegistroService hotelRegistroService;

    @Autowired
    private RegistroHotelRepository registroHotelRepository;

    @PostMapping("/hotel-registrado")
    public String mensajeGuardarHotel(@RequestBody Hotel hotel){
        hotelRegistroService.guardarRegistroHotel(hotel);

        return "Hotel Registrado correctamente";
    }



}
