package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
    public class HotelController {
    @Autowired
    private HotelService hotelService;


//    @GetMapping("/")
//    public String hotel(Model model, Model model1){
//        Hotel hotel = new Hotel();
//        Reserva reserva = new Reserva();
//        model.addAttribute("titulo","Inicio - Travel Planet");
//        model.addAttribute("hotel", hotel);
//        model1.addAttribute("reserva", reserva);
//        return "index";
//    }

   /* @GetMapping("/listar")
    public List<Hotel> listarHoteles(){
        return hotelService.Buscar();
    }

    */

  @GetMapping ("/listar")
    public String procesarBusqueda(@RequestParam(name = "ciudad") String ciudades,
                                   @RequestParam(name = "fecha_inicio") Date fecha_inicio,
                                   @RequestParam(name = "fecha_fin") Date fecha_fin,
                                   Model model, Model model1) {
      List<Hotel> hotel = hotelService.Buscar(ciudades, fecha_inicio, fecha_fin);
      //Reserva reserva = hotelService.search(fecha_inicio, fecha_fin);
      model.addAttribute("titulo","Buscar - Travel Planet");
      model.addAttribute("hotel", hotel);
     // model1.addAttribute("reserva", reserva);
            return "resultado1";

        }

}
