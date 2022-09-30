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

@SessionAttributes("hotel")
    public class HotelController {
    @Autowired
    private HotelService hotelService;
    private HotelRepository hotelRepository;

    @GetMapping("/")
    public String hotel(Model model){
        Hotel hotels = new Hotel();
        Reserva reserva = new Reserva();
        Habitacion capacidad = new Habitacion();
        model.addAttribute("titulo","Registro - Travel Planet");
        model.addAttribute("hotel", hotels);
        model.addAttribute("Sevilla", "Cádiz");
        model.addAttribute("reservas", reserva);
        model.addAttribute("capacidad", capacidad);
        return "index";
    }

    @PostMapping("/")
    public String procesarBusqueda(String ciudades, java.util.Date fechaInicio, Date fechaFin, Integer capacidad) {

        if (hotelRepository.search(ciudades, fechaInicio, fechaFin, capacidad) != null) {
            return "/";
        }
        return "redirect:/disponible";
    }

    @GetMapping("/disponible")
    public String hotelreserva(Model model, @Param("Ciudad") String ciudades,
                               @Param("Fecha inicio") Date fecha_inicio,
                               @Param("Fecha Fin") Date fecha_fin,
                               @Param("Capacidad") Integer capacidad){
        List<Hotel> hotels = new ArrayList<>();
        hotels = hotelService.listAll(ciudades, fecha_inicio, fecha_fin, capacidad);
        model.addAttribute("titulo","Registro - Travel Planet");
        model.addAttribute("hotel", hotels);
        return "resultado1";
    }

}
