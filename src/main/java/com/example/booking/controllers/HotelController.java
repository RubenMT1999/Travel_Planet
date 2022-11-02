package com.example.booking.controllers;

import Paginador.PageRender;
import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.services.HabitacionService;
import com.example.booking.services.HotelService;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

    @Controller
    @RequestMapping("/hoteles")
    @SessionAttributes({"habitacion","reserva","tarifa"})

    public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HabitacionService habitacionService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public String procesarBusqueda(@RequestParam(name = "ciudad") String ciudades,
                                   @Valid Reserva reserva1, BindingResult result,
                                   RedirectAttributes flash,
                                   @RequestParam(name = "fechaInicio") String fecha_inicio,
                                   @RequestParam(name = "fechaFin") String fecha_fin,
                                   @RequestParam(name = "capacidad") Integer capacidad,
                                   Model model) throws ParseException {

        //Para utilizar el SessionAtribute para capacidad.
        Habitacion habitacion = new Habitacion();
        habitacion.setCapacidad(capacidad);
        model.addAttribute("habitacion", habitacion);

        model.addAttribute("titulo", "Buscar - Travel Planet");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = formato.parse(fecha_inicio);
        Date fecha_Fin = formato.parse(fecha_fin);

        //Para utilizar tener un minimo de dias y maximo de dias que se puede pedir un hotel.
        long tiempo = fecha_Fin.getTime() - fechaInicio.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long diffrence = time.convert(tiempo, TimeUnit.MILLISECONDS);
        Integer diferencia = (int) (long) diffrence;
        //Errores de buscador por la fecha.
        if(diferencia < 0){
            flash
                    .addFlashAttribute("mensaje", "La fecha fin es anterior a la fecha inicial")
                    .addFlashAttribute("clase", "success");
            return "redirect:/";
        } else if (diferencia > 30) {
            flash
                    .addFlashAttribute("mensaje", "La fecha fin excede los 30 días")
                    .addFlashAttribute("clase", "success");
            return "redirect:/";
        }

        //Para utilizar el Sessionatribute para fecha inicio y fecha fin.
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fecha_Fin);
        model.addAttribute("reserva", reserva);

        List<Hotel> hotel = hotelService.Buscar(ciudades, fechaInicio, fecha_Fin, capacidad);
        model.addAttribute("hotel", hotel);
        return "busquedahoteles";

    }
    @GetMapping("/habitacion/{id}")
    public String hotelid(@PathVariable(name = "id") Integer id,
                          @ModelAttribute("habitacion") Habitacion capacidad,
                          @ModelAttribute("reserva") Reserva fecha_inicio,
                          @ModelAttribute("reserva") Reserva fecha_fin,Model model) {
        model.addAttribute("titulo", "Buscar - Travel Planet");
        List<Habitacion> habitacions = habitacionService.buscarporoidHabitacion(id, capacidad.getCapacidad(), fecha_inicio.getFechaInicio(), fecha_fin.getFechaFin());
        Hotel hotel = hotelService.hotelID(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitacions", habitacions);
        return "hoteldetalle";
    }


    @GetMapping("/listarHoteles")
 public String hoteles(@RequestParam(name="page", defaultValue = "0") int page, Model model){
//        List<Hotel> hoteles = hotelService.verTodosHoteles();
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Hotel> hotel = hotelService.findAll(pageRequest);
        PageRender<Hotel> pageRender = new PageRender<>("/hoteles/listarHoteles",hotel);
        model.addAttribute("hoteles", hotel);
        model.addAttribute("page",pageRender);
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
        public String hotelNuevo(@ModelAttribute("hotel") Hotel hotel, Authentication auth, @RequestParam(value = "file") MultipartFile imagen,  RedirectAttributes flash){
        hotel.setUsuario(usuarioService.buscarPorMail(auth.getName()));
        hotelService.hotelGuardar(imagen, flash, hotel);
        return "redirect:/hoteles/verHotelesUsuarios";

        }

//        @GetMapping("/nuevo/{id}")
//        public String mostrarHotelNuevo(Model model, @PathVariable Integer id){
//            Hotel hotel = new Hotel();
//            hotel.setUsuario( usuarioRepository.findById(id).get());
//
//            model.addAttribute("hotel",hotel);
//            return "hotelNuevo";
//
//        }
//
//        @PostMapping("/nuevo")
//        public String hotelNuevo(@ModelAttribute("hotel") Hotel hotel){
//            hotelService.crearHotel(hotel.getNombre(),hotel.getPuntuacion(), hotel.getPrecio(),hotel.getComentario(),hotel.getImagen(),hotel.getLugar(),hotel.getTelefono(),hotel.getCif(),hotel.getNumero_habitaciones(),hotel.getCiudad(), hotel.getUsuario().getId());
//            return "redirect:/hoteles/verHotelesUsuarios";
//        }

        @GetMapping("/editar/{id}")
        public String mostrarFormrHotelEditar(@PathVariable int id, Model model){
        model.addAttribute("hotel", hotelService.hotelID(id));
        return "hotelEditar";
        }

        @PostMapping("/editar/{id}")
        public String mostrarHotelEditar(@PathVariable int id, @ModelAttribute("hotel") Hotel hotel,Model model,  @RequestParam(value = "file") MultipartFile imagen,  RedirectAttributes flash){
        Hotel hotelEditar = hotelService.hotelID(id);
        hotelEditar.setId(id);
        hotelEditar.setNombre(hotel.getNombre());
            hotelEditar.setCiudad(hotel.getCiudad());
            hotelEditar.setLugar(hotel.getLugar());
            hotelEditar.setNumero_habitaciones(hotel.getNumero_habitaciones());
            hotelEditar.setPrecio(hotel.getPrecio());
            hotelEditar.setImagen(hotel.getImagen());
            hotelEditar.setTelefono(hotel.getTelefono());
            hotelEditar.setCif(hotel.getCif());
            hotelEditar.setComentario(hotel.getComentario());
            hotelEditar.setEstrellas(hotel.getEstrellas());


            hotelService.cargarImagen(imagen, flash, hotel);

            if (hotel.getImagen() == null) {
                hotelEditar.setImagen(hotelService.imagenHotel(hotel.getId()));
                hotelService.hotelEditar(imagen,flash,hotelEditar);
            }else {
                hotelService.hotelEditar(imagen,flash,hotelEditar);
            }



            return "redirect:/hoteles/verHotelesUsuarios";
        }



        @RequestMapping("/eliminarPorId/{id}")
        public String eliminarPorId( @PathVariable int id,RedirectAttributes flash,Model model){
           hotelService.hotelEliminar(id);
            flash.addFlashAttribute("success","Hotel eliminado con éxito!");
            model.addAttribute("success","Hotel borrado con éxito!");

            return "redirect:/hoteles/verHotelesUsuarios";
        }

        @GetMapping("/verHotelesUsuarios")
        public String listarHotelesUsuario(Model model, Authentication auth){
            auth = SecurityContextHolder.getContext().getAuthentication();
            List<Hotel> hoteles = hotelService.hotelesMail(auth.getName());
            model.addAttribute("hoteles",hoteles);

            return "hotelesUsuario";
        }
}
