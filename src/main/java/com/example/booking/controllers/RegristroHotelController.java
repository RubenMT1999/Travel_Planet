package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.repository.RegistroHotelRepository;
import com.example.booking.services.HotelRegistroService;
import com.example.booking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class RegristroHotelController {

    @Autowired
    private  HotelRegistroService hotelRegistroService;

    @Autowired
    private RegistroHotelRepository registroHotelRepository;
    @PostMapping("/registrar-hotel")
    public void registroHotel(@RequestBody Hotel hotel){

        registroHotelRepository.save(hotel);
    }



}
