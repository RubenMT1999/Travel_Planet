package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Usuario;
import com.example.booking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HotelController2 {

    @Autowired
    HotelService hotelService;

    @PostMapping("/buscarHotel")
    public String buscar(Model model, Hotel hotel){
        model.addAttribute("titulo","Buscar hotel - Travel Planet");

        List<Hotel> hoteles = hotelService.obtenerHoteles(hotel.getCiudad());

        model.addAttribute("hoteles",hoteles);

        return "registro";
    }



}
