package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
    @RequestMapping("/hoteles")
    @SessionAttributes({"habitacion","reserva"})

    public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HabitacionService habitacionService;

    @GetMapping("/listar")
    public String procesarBusqueda(@RequestParam(name = "ciudad") String ciudades,
                                   @RequestParam(name = "fechaInicio") String fecha_inicio,
                                   @RequestParam(name = "fechaFin") String fecha_fin,
                                   @RequestParam(name = "capacidad") Integer capacidad,
                                   Model model) throws ParseException {
        //Para utilizar el SessionAtribute para capacidad.
        Habitacion habitacion = new Habitacion();
        habitacion.setCapacidad(capacidad);
        model.addAttribute("habitacion", habitacion);

        model.addAttribute("titulo", "Buscar - Travel Planet");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = formato.parse(fecha_inicio);
        Date fecha_Fin = formato.parse(fecha_fin);

        //Para utilizar el Sessionatribute para fecha inicio y fecha fin.
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fecha_Fin);
        model.addAttribute("reserva", reserva);

        List<Hotel> hotel = hotelService.Buscar(ciudades, fechaInicio, fecha_Fin, capacidad);

        model.addAttribute("hotel", hotel);
        return "busquedahoteles";

    }
    @GetMapping("/habitacion/{id}")
    public String hotelid(@PathVariable(name = "id") Integer id,
                          @ModelAttribute("habitacion") Habitacion capacidad,
                          @ModelAttribute("reserva") Reserva fecha_inicio,
                          @ModelAttribute("habitacion") Reserva fecha_fin,Model model) {
        model.addAttribute("titulo", "Buscar - Travel Planet");
        List<Habitacion> habitacions = habitacionService.buscarporoidHabitacion(id, capacidad.getCapacidad(), fecha_inicio.getFechaInicio(), fecha_fin.getFechaFin());
        Hotel hotel = hotelService.hotelID(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitacions", habitacions);
        return "hoteldetalle";
    }


    @GetMapping("/listarHoteles")
 public String hoteles(Model model){
        List<Hotel> hoteles = hotelService.verTodosHoteles();
        model.addAttribute("hoteles", hoteles);
    return "hoteles";
    }

    @RequestMapping("/BuscarPorCiudad/{ciudad}")
    public String hotelPorCiudad(Model model, @PathVariable String ciudad){
    List<Hotel> hotelesCiudad = hotelService.hotelPorCiudad(ciudad);
    model.addAttribute("hotelesCiudad", hotelesCiudad);
    return "HotelesPorCiudad";
    }

        @RequestMapping("/BuscarPorId/{id}")
        public String hotelPorId(Model model, @PathVariable int id){
            Hotel hotelesId = hotelService.hotelID(id);
            model.addAttribute("hotelesId", hotelesId);
            return "HotelesId";
        }

        @GetMapping("/nuevo")
        public String mostrarHotelNuevo(Model model){
        Hotel hotel = new Hotel();
        model.addAttribute("hotel",hotel);
        return "hotelNuevo";

        }

        @PostMapping("/nuevo")
        public String hotelNuevo(@ModelAttribute("hotel") Hotel hotel){
        hotelService.hotelGuardar(hotel);
        return "redirect:/hoteles/listarHoteles";

        }
}
