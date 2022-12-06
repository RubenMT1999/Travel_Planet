package com.example.booking.controllers;

import com.example.booking.models.*;
import com.example.booking.repository.AuthoritiesRepository;
import com.example.booking.repository.ReservaRepository;
import com.example.booking.repository.UserAuthRepository;
import com.example.booking.repository.UsuarioRepository;
import com.example.booking.services.*;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import viewPdf.reservaExporterPdf;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes({"reservaUsuario","reservaHotel"})
@RequestMapping("/perfil")
public class PerfilUsuarioController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TarifaService tarifaService;
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private HabitacionService habitacionService;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private PagoService pagoService;


    @GetMapping("/datos")
    public String datosUsuario(Model model, Authentication auth){
        auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario datosUsuario = usuarioService.datosUsuario(auth.getName());


        model.addAttribute("datosUsuario", datosUsuario);
        model.addAttribute("titulo", "Perfil de Usuario");
        return "perfilUsuario";
    }

    @PostMapping("/datos")
    public String getAdminUser(Model model, Authentication auth, Integer valor){
        auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario userAdmin = usuarioService.datosUsuario(auth.getName());
        UserAuth userIDAuth = usuarioService.getUserAuth(auth.getName());
        Authorities authorities = usuarioService.getAuthorities(userIDAuth.getId());

        if (userAdmin.getEsHotelero() == false){
            userAdmin.setEsHotelero(true);
            usuarioService.getAdmin(userAdmin.getEsHotelero(), userAdmin.getId());

            authorities.setAuthority(ERoles.ROLE_ADMIN.toString());
            usuarioService.getRoleAdmin(authorities.getAuthority(), userIDAuth.getId());



        }

        model.addAttribute("getAdmin", userAdmin);
        return "redirect:/perfil/datos";
    }

    @GetMapping("/editar-perfil")
    public String editarPerfil(Model model, Authentication auth){
        auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario editarPerfilUsuario = usuarioService.datosUsuario(auth.getName());
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        model.addAttribute("editarPerfil", editarPerfilUsuario);
        model.addAttribute("titulo", "Editar Perfil");
        return "editarPerfilUsuario";
    }

    @PostMapping("/editar-perfil")
    public String guardarDatos(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model, Authentication auth){
        auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario editarPerfilUsuario = usuarioService.datosUsuario(auth.getName());
        usuario.setId(editarPerfilUsuario.getId());

        if (usuario.getContrasenia().isBlank()){usuario.setContrasenia(editarPerfilUsuario.getContrasenia());}
        if (usuario.getNombre().isBlank()){usuario.setNombre(editarPerfilUsuario.getNombre());}
        if (usuario.getApellidos().isBlank()){ usuario.setApellidos(editarPerfilUsuario.getApellidos()); }
        if (usuario.getFechaNacimiento() == null){ usuario.setFechaNacimiento(editarPerfilUsuario.getFechaNacimiento());}
        if (usuario.getDni().isBlank()){usuario.setDni(editarPerfilUsuario.getDni());}
        if (usuario.getTelefono().isBlank()){usuario.setTelefono(editarPerfilUsuario.getTelefono());}
        if (usuario.getNacionalidad().isBlank()){usuario.setNacionalidad(editarPerfilUsuario.getNacionalidad());}

        String bcryptPassword = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(bcryptPassword);

        usuarioService.editarUsuario(usuario.getNombre(),usuario.getApellidos(),usuario.getContrasenia(),
                usuario.getFechaNacimiento(),usuario.getDni(),usuario.getNacionalidad(),usuario.getTelefono(), usuario.getId());

        return "redirect:/perfil/datos";
    }

    @GetMapping("/mis-hoteles")
    public String listarHotelesUsuario(Model model, Authentication auth){
        auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario nombreUsuario = usuarioService.datosUsuario(auth.getName());
        List<Hotel> hotelesUsuario = hotelService.hotelesMail(auth.getName());
        model.addAttribute("usuario", nombreUsuario);
        model.addAttribute("hotelesUsuario",hotelesUsuario);
        if (nombreUsuario.getHoteles() == null){
            model.addAttribute("hotelNull","No tienes registrado ningun Hotel");
        }
        return "hotelesPerfilUsuario";
    }


    @GetMapping("/mis-reservas")
    public String reservasUsuario( Model model, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario nombreUsuario = usuarioService.datosUsuario(authentication.getName());
        List<Reserva> obtenerReservaUsuario = reservaService.obtenerReservaUsuario(nombreUsuario.getId());


        model.addAttribute("datosReserva", obtenerReservaUsuario);
        model.addAttribute("nombreUsuarioReserva", nombreUsuario);

        return "reservaPerfilUsuario";
    }

    @GetMapping("/exportarPDF/{id}")
    public  void exportarFacturaPdf(HttpServletResponse response, @PathVariable Integer id) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        Reserva reserva = reservaService.obtenerDetallesReserva(id);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String fechaInicio = dateFormatter.format(reserva.getFechaInicio());
        String nombre = reserva.getUsuario().getNombre();
        String apellido = reserva.getUsuario().getApellidos();
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename= Factura_" + fechaInicio + "_" + nombre + "_" + apellido + ".pdf";

        response.setHeader(cabecera, valor);



        reservaExporterPdf exporter = new reservaExporterPdf(reserva);
        exporter.exportar(response);


    }

    @GetMapping("/mis-reservas/{id}")
    public String detallesReserva(Model model, @PathVariable Integer id, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario nombreUsuario = usuarioService.datosUsuario(authentication.getName());
        Reserva reservaPorId = reservaService.obtenerDetallesReserva(id);

        Habitacion habitacion = habitacionService.obtenerHabitacionReserva(reservaPorId.getHabitacion().getId());
        reservaPorId.setHabitacion(habitacion);

        model.addAttribute("reservaHotel", habitacion);
        model.addAttribute("reservaUsuario", reservaPorId);

        model.addAttribute("detallesReserva", reservaPorId);
        model.addAttribute("nombreUsuarioDetallesReserva", nombreUsuario);

        return "detallesReserva";
    }

    @GetMapping("/editar/{id}")
    public String editarReserva(@PathVariable Integer id, Model model, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario datosUsuario = usuarioService.datosUsuario(authentication.getName());
        Reserva reserva = reservaService.obtenerDetallesReserva(id);

        model.addAttribute("editarReserva", reserva);
        model.addAttribute("editarReservaUsuario", datosUsuario);

        return "";
    }

    @RequestMapping("/eliminar-reserva/{id}")
    public String cancelarReserva(@PathVariable Integer id, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario nombreUsuario = usuarioService.datosUsuario(authentication.getName());
        Reserva reserva = reservaService.obtenerDetallesReserva(id);
        reserva.setUsuario(nombreUsuario);
        reservaService.cancelarReserva(reserva);
        habitacionService.editarDisponibilidad(true, reserva.getHabitacion().getId());
        return "redirect:/perfil/mis-reservas";
    }


    @GetMapping("/mis-reservas/pago")
    public String pagarReserva(Model model,@ModelAttribute("reservaUsuario") Reserva reservaUsuario,
                               @ModelAttribute("reservaHotel") Habitacion reservaHotel, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario nombreUsuario = usuarioService.datosUsuario(authentication.getName());
        Pago pago = new Pago();
        Hotel hotel = reservaHotel.getHotel();


        pago.setId_reserva(reservaUsuario);
        pago.setId_usuario(nombreUsuario);

        model.addAttribute("titulo","Pago de Reserva");
        model.addAttribute("metodoPago", pago);
        model.addAttribute("datosReservaHotel", hotel);
        model.addAttribute("nombreUsuarioPago", nombreUsuario);

        return "pago";
    }

    @PostMapping("/mis-reservas/pago")
    public String efectuarPago(@Valid @ModelAttribute("reservaUsuario") Reserva reservaUsuario ,BindingResult result, Model model,
                               Authentication authentication, Pago pago, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "perfil/mis-reservas/pago";
        }

        authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario nombreUsuario = usuarioService.datosUsuario(authentication.getName());

        pago.setId_usuario(nombreUsuario);
        Reserva reserva = reservaUsuario;
        pago.setId_reserva(reserva);
        pagoService.guardarPago(pago);
        Habitacion habitacion = pago.getId_reserva().getHabitacion();

        reservaService.editarPagado(true, habitacion.getId());

        model.addAttribute("metodoPago", pago);


        redirectAttributes.addFlashAttribute("success", "La Reserva se ha pagado con éxito");


        return "redirect:/perfil/mis-reservas";
    }


}
