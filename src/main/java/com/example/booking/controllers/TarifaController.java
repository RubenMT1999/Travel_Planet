package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Pension;
import com.example.booking.models.Tarifa;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HotelService;
import com.example.booking.services.PensionService;
import com.example.booking.services.TarifaService;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/tarifa")
public class TarifaController {
    @Autowired
    TarifaService tarifaService;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelService hotelService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    PensionService pensionService;

    @GetMapping("/nuevo/{id}")
    public String mostrarTarifaNueva(@PathVariable Integer id, Model model){
        Tarifa tarifa = new Tarifa();
        Integer id_hotel = id;

        model.addAttribute("tarifa",tarifa);
        model.addAttribute("id_hotel", id_hotel);
        return "tarifaNueva";

    }

    @PostMapping("/nuevo")
    public String tarifaNueva(@Valid Tarifa tarifa, Model model,@RequestParam ("id_hotel")Integer id_hotel){

        tarifaService.guardarTarifa(tarifa.getPrecioBanio(),tarifa.getPrecioCajaFuerte(),tarifa.getPrecioCocina(),tarifa.getPrecioTV(),tarifa.getPrecioTerraza(),tarifa.getPrecioWifi(),tarifa.getPrecioAire(),id_hotel);

        return "redirect:/hoteles/verHotelesUsuarios";

    }

    @GetMapping("/pension/{id}")
    public  String mostrarTarifa(@PathVariable Integer id, Model model){
        Tarifa tarifa = new Tarifa();
        Pension pension = tarifa.getPension();
        Integer id_tarifa = id;
        model.addAttribute("pension", tarifa);
        model.addAttribute("id_hotel", id_tarifa);
        return "pensionNueva";

    }
}
