package com.example.booking.controllers;

import com.example.booking.models.Usuario;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UsuarioController {


    @Autowired
    UsuarioService usuarioService;


    @RequestMapping("/usuarios")
    public String listaUsuarios(Model model){
        List<Usuario> usuarios = usuarioService.getAll();
        model.addAttribute("usuarios",usuarios);
        model.addAttribute("titulo","Clientes");
        return "listaUsuarios";
    }

    @GetMapping("/registro")
    public String registro(Model model){
        model.addAttribute("titulo","Registro - Travel Planet");
        return "registro";
    }


}
