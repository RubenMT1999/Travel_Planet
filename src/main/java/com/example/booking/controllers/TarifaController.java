package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Pension;
import com.example.booking.models.PensionHotel;
import com.example.booking.models.Tarifa;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HotelService;
//import com.example.booking.services.PensionService;
import com.example.booking.services.TarifaService;
import com.example.booking.services.UsuarioService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
//    @Autowired
//    PensionService pensionService;

    @GetMapping("/nuevo/{id}")
    public String mostrarTarifaNueva(@PathVariable Integer id, Model model){
        if ( tarifaService.verTarifa(id) != null){
            model.addAttribute("error", "Ya existen características");
            return "redirect:/tarifa/editar/{id}";
        }else{

        PensionHotel pension1 = new PensionHotel();
        PensionHotel pension2 = new PensionHotel();
        PensionHotel pension3 = new PensionHotel();
        PensionHotel pension4 = new PensionHotel();
        Tarifa tarifa = new Tarifa();
        Tarifa tari = new Tarifa();
        Integer id_hotel = id;

            List<PensionHotel> pensiones = new ArrayList<>();
            pensiones.add(pension1);
            pensiones.add(pension2);
            pensiones.add(pension3);
            pensiones.add(pension4);

            model.addAttribute("pension1", pension1);
            model.addAttribute("pension2", pension2);
            model.addAttribute("pension3", pension3);
            model.addAttribute("pension4", pension4);
        model.addAttribute("tarifa",tarifa);
        model.addAttribute("id_hotel", id_hotel);

        return "tarifaNueva";
        }
    }

    @PostMapping("/nuevo")
    public String tarifaNueva(@Valid Tarifa tarifa, Model model, @RequestParam ("id_hotel")Integer id_hotel,
                              @Valid @ModelAttribute(name = "pension1") PensionHotel pension1,
                              @Valid @ModelAttribute(name = "pension2") PensionHotel pension2,
                              @Valid @ModelAttribute(name = "pension3") PensionHotel pension3,
                              @Valid @ModelAttribute(name = "pension4") PensionHotel pension4){


            tarifaService.guardarTarifa(tarifa.getPrecioBanio(),tarifa.getPrecioCajaFuerte(),tarifa.getPrecioCocina(),tarifa.getPrecioTV(),tarifa.getPrecioTerraza(),tarifa.getPrecioWifi(),tarifa.getPrecioAire(),id_hotel);
            Tarifa tarifaHotel = tarifaService.verTarifa(id_hotel);
            Integer id_tarifa = tarifaHotel.getId();




            tarifaService.guardarPension(pension1.getPension().ordinal(), pension1.getPrecio(), id_tarifa);
            tarifaService.guardarPension(pension2.getPension().ordinal(), pension2.getPrecio(), id_tarifa);
            tarifaService.guardarPension(pension3.getPension().ordinal(), pension3.getPrecio(), id_tarifa);
            tarifaService.guardarPension(pension4.getPension().ordinal(), pension4.getPrecio(), id_tarifa);




            return "redirect:/hoteles/verHotelesUsuarios";




    }

//    @GetMapping("/pension/{id}")
//    public  String mostrarTarifa(@PathVariable Integer id, Model model){
//        Tarifa tarifa = new Tarifa();
//        Pension pension = tarifa.getPension();
//        Integer id_tarifa = id;
//        model.addAttribute("pension", tarifa);
//        model.addAttribute("id_hotel", id_tarifa);
//        return "pensionNueva";

//    }

    @GetMapping("/editar/{id}")
    public String mostrarTarifaEditar(@PathVariable int id, Model model){

        if ( tarifaService.verTarifa(id) == null){
            model.addAttribute("error", "No existen características");
            return "redirect:/hoteles/verHotelesUsuarios";
        }else{



        Integer id_hotel = id;
        model.addAttribute("tarifa", tarifaService.verTarifa(id));
        model.addAttribute("id_hotel", id_hotel);
        return "tarifaEditar";
        }
    }

    @PostMapping("/editar")
    public String mostrarTarifaEditar(@ModelAttribute("tarifa") Tarifa tarifa,Model model, @RequestParam ("id_hotel")Integer id_hotel){
        Tarifa tarifaEditar = tarifaService.verTarifa(id_hotel);
        tarifaEditar.setPrecioWifi(tarifa.getPrecioWifi());
        tarifaEditar.setPrecioCajaFuerte(tarifa.getPrecioCajaFuerte());
        tarifaEditar.setPrecioCocina(tarifa.getPrecioCocina());
        tarifaEditar.setPrecioAire(tarifa.getPrecioAire());
        tarifaEditar.setPrecioTV(tarifa.getPrecioTV());
        tarifaEditar.setPrecioTerraza(tarifa.getPrecioTerraza());
        tarifaEditar.setPrecioBanio(tarifa.getPrecioBanio());

        tarifaService.tarifaEditar(tarifaEditar);



        return "redirect:/hoteles/verHotelesUsuarios";
    }






}
