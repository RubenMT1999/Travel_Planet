package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.HotelRepository;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.valueOf;

@RequestMapping("/habitaciones")
@Controller
@SessionAttributes("habitacion")
public class HabitacionController {

    @Autowired
    HabitacionService habitacionService;
    @Autowired
    HotelService hotelService;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HabitacionRepository habitacionRepository;

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
    public String procesar(@Valid Habitacion habitacion, BindingResult result, Model model,
                           @RequestParam("file") MultipartFile imagen, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "crearHabitacion";
        }
        if (!imagen.isEmpty()){
            Path directorioRecursosImagenesHabitaciones = Paths.get("src//resources//static/images");
            String rootPath = directorioRecursosImagenesHabitaciones.toFile().getAbsolutePath();
            try {
                byte[] bytes =  imagen.getBytes();
                Path rutaCompleta = Paths.get(rootPath + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta,bytes);
                // añadir flash.addFlashAttribute

                habitacion.setImagen(imagen.getOriginalFilename());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        habitacionService.guardarHabitacion(habitacion);
        status.setComplete();
        return "index";
    }


//  CUIDADO CON ESTO, ES PARA GENERAR 10 HABITACIONES POR CADA HOTEL
//    @GetMapping("/generar")
//    public void generarhabitacionesAleatorio() {
//
//        Faker faker = new Faker();
//        Integer contadorHabitacion = 528;
//
//        for (Hotel h : hotelRepository.obtenerTodoshoteles()){
//
//            Set<Habitacion> habitaciones = new HashSet<>();
//            for (int x = 0; x < 10; x ++){
//                Habitacion habitacion = new Habitacion();
//                habitacion.setId(contadorHabitacion);
//                habitacion.setHotel(h);
//                habitacion.setNumeroHabitacion(faker.number().numberBetween(1,500));
//                habitacion.setExtensionTelefonica(valueOf(faker.number().numberBetween(100,999)));
//                habitacion.setCapacidad(faker.number().numberBetween(1, 9));
//                habitacion.setDescripcion(faker.lorem().fixedString(100));
//                habitacion.setDisponibilidad(faker.bool().bool());
//                habitacion.setImagen("https://www.cataloniahotels.com/es/blog/wp-content/uploads/2016/05/habitaci%C3%B3n-doble-catalonia-620x412.jpg");
//                contadorHabitacion ++;
//                habitacionService.guardarHabitacion(habitacion);
//                habitaciones.add(habitacion);
//                h.setHabitaciones(habitaciones);
//            }
//            hotelRepository.save(h);
//        }
//
//    }



//    @GetMapping("/editar/{id}")
//    public String editar(Model model, @PathVariable Integer id){
//
//    }



}
