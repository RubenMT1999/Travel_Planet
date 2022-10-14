package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

    @Controller
    @RequestMapping("/hoteles")
    public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HabitacionService habitacionService;

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
