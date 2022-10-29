package com.example.booking.controllers;

import com.example.booking.models.Usuario;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class PerfilUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/datos")
    public String datosUsuario(Model model, Authentication auth){
        auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario datosUsuario = usuarioService.datosUsuario(auth.getName());
        model.addAttribute("datosUsuario", datosUsuario);
        return "perfilUsuario";
    }

}
