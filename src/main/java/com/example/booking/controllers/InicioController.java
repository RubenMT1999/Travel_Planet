package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Controller
public class InicioController {
    @Autowired
    HabitacionService habitacionService;

    @Autowired
    HotelService hotelService;

    @GetMapping("/")
    public String hotel(Model model) {
        Hotel hotel = new Hotel();
        Reserva reserva = new Reserva();
        Habitacion habitacion = new Habitacion();
        model.addAttribute("titulo", "Inicio - Travel Planet");
        model.addAttribute("hotel", hotel);
        model.addAttribute("reserva", reserva);
        model.addAttribute("capacidad", habitacion);
        return "index";
    }


}
