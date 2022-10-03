package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegristroHotelController {

    @GetMapping("/registrar-hotel")
    public String registroHotel(Model model){
        Hotel nuevoHotel = new Hotel();
        model.addAttribute("titulo", "Registro de Hotel - Travel Planet");
        model.addAttribute("nuevoHotel", nuevoHotel);
        return "registro-Hotel";
    }

}
