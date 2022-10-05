package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.models.Usuario;
import com.example.booking.repository.HotelRepository;
import com.example.booking.repository.UsuarioRepository;
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
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/")
    public String hotel(Model model){
        Hotel hotel = new Hotel();
        model.addAttribute("titulo","Inicio - Travel Planet");
        model.addAttribute("hotel", hotel);
        return "index";
    }

   /* @GetMapping("/listar")
    public List<Hotel> listarHoteles(){
        return hotelService.Buscar();
    }

    */

  @PostMapping ("/listar")
    public String procesarBusqueda(@RequestParam Hotel ciudades, Model model) {
      List<Hotel> hotel = hotelService.Buscar(ciudades);
      model.addAttribute("titulo","Buscar - Travel Planet");
      model.addAttribute("hotel", hotel);
            return "resultado1";

        }

}
