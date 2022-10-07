package com.example.booking.controllers;


import com.example.booking.models.Reserva;
import com.example.booking.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/ver")
    public String listarReserva(Model model){
        List<Reserva> reservas = reservaService.listar();
        model.addAttribute("titulo", "Mis Reservas");
        model.addAttribute("reservas",reservas);
        return "reservas";
    }



}
