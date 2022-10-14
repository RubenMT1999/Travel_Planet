package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.services.DetalleHabitacionService;
import com.example.booking.services.DetalleHotelService;
import com.example.booking.services.HotelService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
    public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private DetalleHotelService detalleHotelService;
    @Autowired
    private DetalleHabitacionService detalleHabitacionService;



    @GetMapping("/")
    public String hotel(Model model) {
        Hotel hotel = new Hotel();
        Reserva reserva = new Reserva();
        model.addAttribute("titulo", "Inicio - Travel Planet");
        model.addAttribute("hotel", hotel);
        model.addAttribute("reserva", reserva);
        return "index";
    }

    @GetMapping("/hotel/{id}")
    public String hotelid(@PathVariable(name = "id") Integer id, Model model) {
        List<Habitacion> habitacions = detalleHabitacionService.buscarporoidHabitacion(id);
        Hotel hotel = detalleHotelService.buscarporid(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitacions", habitacions);
        return "hoteldetalle";
    }


    @GetMapping("/listar")
    public String procesarBusqueda(@RequestParam(name = "ciudad") String ciudades,
                                   @RequestParam(name = "fechaInicio") String fecha_inicio,
                                   @RequestParam(name = "fechaFin") String fecha_fin,
                                   Model model) throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = formato.parse(fecha_inicio);
        Date fecha_Fin = formato.parse(fecha_fin);

        List<Hotel> hotel = hotelService.Buscar(ciudades, fechaInicio, fecha_Fin);
        model.addAttribute("titulo", "Buscar - Travel Planet");
        model.addAttribute("hotel", hotel);
        return "busquedahoteles";

    }

   /* @GetMapping("/generar")
    public void generarhabitacionesAleatorio() {
        Faker faker = new Faker();
        List<Habitacion> habitacionList = new ArrayList<>();
        Habitacion habitacion = new Habitacion();
        for (int i = 0; i < 100; i++) {
            habitacion.setNumeroHabitacion(faker.number().randomDigitNotZero());
            habitacion.setExtensionTelefonica(faker.number().toString());
            habitacion.setCapacidad(faker.number().numberBetween(1, 9));
            habitacion.setDescripcion(faker.lorem().toString());
            habitacion.setDisponibilidad(faker.bool().bool());
            habitacion.setHotel();
            habitacion.setImagen("https://www.cataloniahotels.com/es/blog/wp-content/uploads/2016/05/habitaci%C3%B3n-doble-catalonia-620x412.jpg");
            habitacion.guardar();
        }
        {
        }


    }

    */
}
