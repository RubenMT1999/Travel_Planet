package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/habitaciones")
@Controller
@SessionAttributes("habitacion")
public class HabitacionController {

    @Autowired
    HabitacionService habitacionService;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/listar/{id}")
    public String listar(Model model, @PathVariable Integer id){
        List<Habitacion> habitaciones = habitacionService.listarHabitaciones(id);
        model.addAttribute("habitaciones",habitaciones);
        return "habitaciones";
    }



    @GetMapping("/crear/{id}")
    public String crear(Model model, @PathVariable Integer id){
        Habitacion habitacion = new Habitacion();
        habitacion.setHotel(hotelRepository.findById(id).get());

        model.addAttribute("titulo","Crear Habitación");
        model.addAttribute("habitacion",habitacion);
        return "crearHabitacion";
    }


    @PostMapping("/crear")
    public String procesar(@Valid Habitacion habitacion, BindingResult result, Model model, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "crearHabitacion";
        }
        habitacionService.guardarHabitacion(habitacion);
        status.setComplete();
        model.addAttribute("success","La habitación ha sido creada con éxito!");
        return "index";
    }



    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable Integer id){
        Habitacion habitacion = habitacionService.encontrarPorId(id);
        model.addAttribute("habitacion",habitacion);
        model.addAttribute("titulo","Editar Habitación");
        return "editarHabitacion";
    }


    @PostMapping("/editar")
    public String postEditar(@Valid Habitacion habitacion, BindingResult result, Model model, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "editarHabitacion";
        }

        habitacionService.guardarHabitacion(habitacion);
        status.setComplete();
        model.addAttribute("success","La habitación ha sido actualizada con éxito!");
        return "index";
    }



    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable("id") Integer id, Model model){
        Habitacion habitacion = habitacionService.encontrarPorId(id);
        habitacionService.borrarHabitacion(habitacion);
        model.addAttribute("success","Habitación borrada con éxito!");
        return "index";
    }



}
