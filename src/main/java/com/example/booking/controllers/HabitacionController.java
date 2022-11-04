package com.example.booking.controllers;

import Paginador.PageRender;
import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Hotel;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.valueOf;

@RequestMapping("/habitaciones")
@Controller
@SessionAttributes({"habitacion","id"})
public class HabitacionController {

    @Autowired
    HabitacionService habitacionService;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelService hotelService;

    @GetMapping({"/listar/{id}"})
    public String listar(Model model, @PathVariable Integer id ,@RequestParam(name="page", defaultValue = "0") int page){
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Habitacion> habitaciones = habitacionService.listarHabitacionesPages(pageRequest,id);
        PageRender<Habitacion> pageRender = new PageRender<>("/habitaciones/listar/"+id,habitaciones);
        model.addAttribute("page",pageRender);
        model.addAttribute("habitaciones",habitaciones);
        model.addAttribute("id",id);
        return "habitaciones";
    }

    /*@ModelAttribute("capacidad")
    @GetMapping("/habitacion/{id}")
    public String hotelid(@PathVariable(name = "id") Integer id,
                          SessionStatus status, Model model) {
        model.addAttribute("titulo", "Buscar - Travel Planet");
        status.setComplete();
        List<Habitacion> habitacions = habitacionService.buscarporoidHabitacion(id);
        Hotel hotel = hotelService.hotelID(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitacions", habitacions);
        return "hoteldetalle";
    }

     */


    @GetMapping("/crear/{id}")
    public String crear(Model model, @PathVariable Integer id){
        Habitacion habitacion = new Habitacion();
        habitacion.setHotel(hotelRepository.findById(id).get());

        model.addAttribute("titulo","Crear Habitación");
        model.addAttribute("habitacion",habitacion);
        return "crearHabitacion";
    }


    @PostMapping("/crear")
    public String procesar(@Valid Habitacion habitacion, BindingResult result, Model model, @RequestParam(value = "file") MultipartFile imagen,
                           SessionStatus status, @ModelAttribute("id") Integer idHotel, RedirectAttributes flash){

        Integer comprobarNumHab = habitacionService.comprobarNumHab(habitacion.getNumeroHabitacion());

        if(result.hasErrors() || comprobarNumHab != null){
            model.addAttribute("titulo", "Ha habido algún error");
            model.addAttribute("comprobarNumHab",comprobarNumHab);
            return "crearHabitacion";
        }

        habitacion.setPrecioBase(habitacionService.establecerPrecioHabitacion(habitacion.getPrecioBase(), habitacion));

        habitacionService.cargarImagen(imagen,flash,habitacion);

        habitacionService.guardarPersonalizado(habitacion.getHotel().getId(),habitacion.getNumeroHabitacion(),habitacion.getExtensionTelefonica(),
                habitacion.getCapacidad(),habitacion.getImagen(),habitacion.getDescripcion(),habitacion.getPrecioBase(),habitacion.isCajaFuerte(),
                habitacion.isCocina(),habitacion.isBanioPrivado(),habitacion.isAireAcondicionado(),habitacion.isTv(),habitacion.isTerraza(),
                habitacion.isWifi());
        model.addAttribute("success","La habitación ha sido creada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }



    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable Integer id){
        Habitacion habitacion = habitacionService.encontrarPorId(id);

        if(habitacion==null){
            model.addAttribute("error","La habitación no existe");
            return "index";
        }

        model.addAttribute("habitacion",habitacion);
        model.addAttribute("titulo","Editar Habitación");
        return "editarHabitacion";
    }



    @PostMapping("/editar")
    public String postEditar(@Valid Habitacion habitacion, BindingResult result, Model model, SessionStatus status,
                             @ModelAttribute("id") Integer idHotel, @RequestParam(value = "file") MultipartFile imagen,
                             RedirectAttributes flash){
        if(result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "editarHabitacion";
        }

        habitacionService.cargarImagen(imagen,flash,habitacion);

        habitacionService.guardarHabitacion(habitacion);
        model.addAttribute("success","La habitación ha sido actualizada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }



    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable("id") Integer id, Model model, @ModelAttribute("id") Integer idHotel,
                         RedirectAttributes flash){


        Habitacion habitacion = habitacionService.encontrarPorId(id);
        habitacionService.borrarImagen(habitacion);
        habitacionService.borrarHabitacion(habitacion);
        flash.addFlashAttribute("success","Habitación eliminada con éxito!");
        model.addAttribute("success","Habitación borrada con éxito!");
        return "redirect:/habitaciones/listar/"+idHotel;
    }

    @GetMapping("/generar")
    public void generarhabitacionesAleatorio() {

        Faker faker = new Faker();
        Integer contadorHabitacion = 1;

        for (Hotel h : hotelRepository.obtenerTodoshoteles()){
            List<Habitacion> habitaciones = new ArrayList<>();
            for (int x = 0; x < 20; x ++){
                Habitacion habitacion = new Habitacion();
                habitacion.setId(contadorHabitacion);
                habitacion.setHotel(h);
                habitacion.setNumeroHabitacion(faker.number().numberBetween(1,500));
                habitacion.setExtensionTelefonica(valueOf(faker.number().numberBetween(100,999)));
                habitacion.setCapacidad(faker.number().numberBetween(1, 9));
                habitacion.setDescripcion(faker.lorem().fixedString(100));
                habitacion.setDisponibilidad(faker.bool().bool());
                habitacion.setImagen("https://www.cataloniahotels.com/es/blog/wp-content/uploads/2016/05/habitaci%C3%B3n-doble-catalonia-620x412.jpg");
                contadorHabitacion ++;
                habitacionService.guardarHabitacion(habitacion);
                habitaciones.add(habitacion);
                h.setHabitaciones(habitaciones);
            }
            hotelRepository.save(h);
        }

    }



}
