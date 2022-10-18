package com.example.booking.controllers;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import com.example.booking.models.Usuario;
import com.example.booking.repository.HotelRepository;
import com.example.booking.repository.UsuarioRepository;
import com.example.booking.services.HotelService;
import com.example.booking.services.IUsuarioService;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    @Controller
    @RequestMapping("/hoteles")
    public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/")
    public String hotel(Model model, Model model1){
        Hotel hotel = new Hotel();
        Reserva reserva = new Reserva();
        model.addAttribute("titulo","Inicio - Travel Planet");
        model.addAttribute("hotel", hotel);
        model1.addAttribute("reserva", reserva);
        return "index";
    }




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
        public String hotelNuevo(@ModelAttribute("hotel") Hotel hotel, Authentication auth){
        hotel.setUsuario(usuarioService.buscarPorMail(auth.getName()));
        hotelService.hotelGuardar(hotel);
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
        public String mostrarHotelEditar(@PathVariable int id, @ModelAttribute("hotel") Hotel hotel,Model model){
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
            hotelEditar.setPuntuacion(hotel.getPuntuacion());

            hotelService.hotelEditar(hotelEditar);
            return "redirect:/hoteles/verHotelesUsuarios";
        }



        @RequestMapping("/eliminarPorId/{id}")
        public String eliminarPorId( @PathVariable int id){
           hotelService.hotelEliminar(id);

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
