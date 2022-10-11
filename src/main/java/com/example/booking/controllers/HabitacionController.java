package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/habitaciones")
@Controller
public class HabitacionController {

    @Autowired
    HabitacionService habitacionService;

    @GetMapping("/listar/{id}")
    public String listar(Model model, @PathVariable Integer id){
        List<Habitacion> habitaciones = habitacionService.listarHabitaciones(id);
        model.addAttribute("habitaciones",habitaciones);
        return "habitaciones";
    }




}
