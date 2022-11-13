package com.example.booking.controllers;

import com.example.booking.models.*;
import com.example.booking.repository.AuthoritiesRepository;
import com.example.booking.repository.UserAuthRepository;
import com.example.booking.repository.UsuarioRepository;
import com.example.booking.services.HotelService;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/perfil")
public class PerfilUsuarioController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/datos")
    public String datosUsuario(Model model, Authentication auth){
        auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario datosUsuario = usuarioService.datosUsuario(auth.getName());


        model.addAttribute("datosUsuario", datosUsuario);
        model.addAttribute("titulo", "Perfil de Usuario");
        return "perfilUsuario";
    }

    @PostMapping("/datos")
    public String getAdminUser(Model model, Authentication auth){
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
        model.addAttribute("editarPerfil", editarPerfilUsuario);
        model.addAttribute("titulo", "Editar Perfil");
        return "editarPerfilUsuario";
    }

    @PostMapping("/editar-perfil")
    public String guardarDatos(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model){

        usuario.setFechaNacimiento(usuario.getFechaNacimiento());
        if (usuario.getContrasenia() == null){
            usuario.setContrasenia(usuario.getContrasenia());
        }

        if (result.hasErrors()){
            return "redirect:/perfil/datos";
        }

        String bcryptPassword = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(bcryptPassword);

        usuarioService.editarUsuario(usuario.getNombre(),usuario.getApellidos(),usuario.getContrasenia(),
                usuario.getFechaNacimiento(),usuario.getDni(),usuario.getNacionalidad(),usuario.getTelefono(),usuario.getEmail());

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
    public String reservasUsuario(Model model, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario nombreUsuario = usuarioService.datosUsuario(authentication.getName());



        return "reservaPerfilUsuario";
    }


}
