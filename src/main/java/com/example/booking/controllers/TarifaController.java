package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Tarifa;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HotelService;
import com.example.booking.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tarifa")
public class TarifaController {
    @Autowired
    TarifaService tarifaService;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelService hotelService;

    @GetMapping("/nuevo/{id}")
    public String mostrarTarifaNueva(@PathVariable int id, Model model){
        Tarifa tarifa = new Tarifa();
        tarifa.setHotelTarifa(hotelRepository.findById(id).get());
        model.addAttribute("tarifa",tarifa);
        return "tarifaNueva";

    }

    @PostMapping("/nuevo")
    public String hotelNuevo(@ModelAttribute("hotel") Tarifa tarifa, Authentication auth){
        tarifaService.tarifaNueva(tarifa);
        return "redirect:/hoteles/verHotelesUsuarios";

    }

}
