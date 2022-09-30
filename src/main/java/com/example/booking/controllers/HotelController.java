package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.models.Usuario;
import com.example.booking.services.HotelService;
import com.example.booking.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller

@SessionAttributes("hotel")

    public class HotelController {
    @Autowired
    private HotelService hotelservice;

    @GetMapping("/hoteles")
    public String hotel(Model model){
        Hotel hotels = new Hotel();
        Reserva reserva = new Reserva();
        model.addAttribute("titulo","Registro - Travel Planet");
        model.addAttribute("hotel", hotels);
        model.addAttribute("reservas", reserva);
        return "resultado1";
    }



}
