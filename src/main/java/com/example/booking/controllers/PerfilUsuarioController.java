package com.example.booking.controllers;

import com.example.booking.models.UserAuth;
import com.example.booking.models.Usuario;
import com.example.booking.repository.UserAuthRepository;
import com.example.booking.repository.UsuarioRepository;
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

@Controller
@RequestMapping("/perfil")
public class PerfilUsuarioController {

    @Autowired
    private UsuarioService usuarioService;
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



}
