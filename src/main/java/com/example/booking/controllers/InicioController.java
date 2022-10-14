package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String hotel(Model model) {
        Hotel hotel = new Hotel();
        Reserva reserva = new Reserva();
        model.addAttribute("titulo", "Inicio - Travel Planet");
        model.addAttribute("hotel", hotel);
        model.addAttribute("reserva", reserva);
        return "index";
    }
}
