package com.example.booking.controllers;


import com.example.booking.models.Reserva;
import com.example.booking.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/ver")
    public List<Reserva> listarReserva(){
        return reservaService.listar();
    }

}
