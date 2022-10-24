package com.example.booking.controllers;


import com.example.booking.models.Habitacion;
import com.example.booking.models.Reserva;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.ReservaService;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/ver")
    public String listarReserva(Model model, Authentication auth){
        auth = SecurityContextHolder.getContext().getAuthentication();
        List<Reserva> reservas = reservaService.reservasPorNombre(auth.getName());
        model.addAttribute("titulo", "Mis Reservas");
        model.addAttribute("reservas",reservas);
        return "reservas";
    }


//    @GetMapping("/crear/{id}")
//    public String crearReserva(Model model, @PathVariable Integer id, Authentication auth){
//        auth= SecurityContextHolder.getContext().getAuthentication();
//        Reserva reserva = new Reserva();
//        Habitacion habitacion = habitacionService.findById(id);
//        reserva.setHabitacion(habitacion);
//        reserva.setUsuario(usuarioService.usuarioPorNombre(auth.getName()));
//        model.addAttribute("titulo","Crear Reserva");
//        model.addAttribute("reserva",reserva);
//        return "crearReserva";
//    }

}
