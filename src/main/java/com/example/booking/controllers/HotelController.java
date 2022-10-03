package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.models.Usuario;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HotelService;
import com.example.booking.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/")
    public String hotel(Model model){
        Hotel hotel = new Hotel();
        model.addAttribute("titulo","Buscar - Travel Planet");
        model.addAttribute("hotel", hotel);
        return "index";
    }

  @PostMapping("/probar")
    public String procesarBusqueda(Model model, String ciudades) {
        if(ciudades == null){
            model.addAttribute("titulo", "ha habido un error en la busqueda");
            return "index";
        }
        return "redirect:/disponible";
    }

    @GetMapping("/disponible")
    public String hotelreserva(Model model, Hotel hotel){


        return "resultado1";
    }



}
