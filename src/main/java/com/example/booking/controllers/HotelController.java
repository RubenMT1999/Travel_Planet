package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.models.Usuario;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HotelService;
import com.example.booking.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller

@SessionAttributes("hotel")
    public class HotelController {
    @Autowired
    private HotelService hotelService;

//    @GetMapping("/")
//    public String hotel(Model model){
//        Hotel hotels = new Hotel();
//        Reserva reserva = new Reserva();
//        model.addAttribute("titulo","Registro - Travel Planet");
//        model.addAttribute("hotel", hotels);
//        model.addAttribute("reservas", reserva);
//        return "index";
//    }

    @PostMapping("/")
    public String procesarBusqueda(String ciudades, java.util.Date fechaInicio, Date fechaFin) {

        if (hotelService.listAll(ciudades, fechaInicio, fechaFin) == null) {
            return "/";
        }
        return "redirect:/disponible";
    }

    @GetMapping("/disponible")
    public String hotelreserva(Model model){
        List<Hotel> hotels = new ArrayList<>();
        model.addAttribute("titulo","Registro - Travel Planet");
        model.addAttribute("hotel", hotels);
        return "resultado1";
    }







}
