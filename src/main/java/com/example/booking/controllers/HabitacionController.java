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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequestMapping("/habitaciones")
@Controller
@SessionAttributes({"habitacion","id"})
public class HabitacionController {

    @Autowired
    HabitacionService habitacionService;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping({"/listar/{id}"})
    public String listar(Model model, @PathVariable Integer id){
        List<Habitacion> habitaciones = habitacionService.listarHabitaciones(id);
        model.addAttribute("habitaciones",habitaciones);
        model.addAttribute("id",id);
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
    public String procesar(@Valid Habitacion habitacion, BindingResult result, Model model, @RequestParam(value = "imagen") MultipartFile imagen,
                           SessionStatus status, @ModelAttribute("id") Integer idHotel, RedirectAttributes flash){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "crearHabitacion";
        }

        if(!imagen.isEmpty()){
            Path directorioRecursos = Paths.get("src/main/resources/static/uploads");
            String rootPath = directorioRecursos.toFile().getAbsolutePath();
            try {
                byte[] bytes = imagen.getBytes();
                Path rutaCompleta = Paths.get(rootPath+"//"+imagen.getOriginalFilename());
                Files.write(rutaCompleta,bytes);
                flash.addFlashAttribute("info","Has subido correctamente '"+ imagen.getOriginalFilename()+"'");

                habitacion.setImagen(imagen.getOriginalFilename());
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        habitacionService.guardarPersonalizado(habitacion.getHotel().getId(),habitacion.getNumeroHabitacion(),habitacion.getExtensionTelefonica(),
                habitacion.getCapacidad(),habitacion.getImagen(),habitacion.getDescripcion());
        model.addAttribute("success","La habitación ha sido creada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }



    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable Integer id){
        Habitacion habitacion = habitacionService.encontrarPorId(id);
        model.addAttribute("habitacion",habitacion);
        model.addAttribute("titulo","Editar Habitación");
        return "editarHabitacion";
    }


    @PostMapping("/editar")
    public String postEditar(@Valid Habitacion habitacion, BindingResult result, Model model, SessionStatus status,
                             @ModelAttribute("id") Integer idHotel){
        if(result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "editarHabitacion";
        }
        habitacionService.guardarHabitacion(habitacion);
        model.addAttribute("success","La habitación ha sido actualizada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }



    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable("id") Integer id, Model model, @ModelAttribute("id") Integer idHotel){
        Habitacion habitacion = habitacionService.encontrarPorId(id);
        habitacionService.borrarHabitacion(habitacion);
        model.addAttribute("success","Habitación borrada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }



}
