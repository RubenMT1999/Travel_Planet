package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
    public class HotelController {
    @RequestMapping("/hotel")
    public String listaUsuarios(Model model){
        List<Usuario> usuarios = hotel.getAll();
        model.addAttribute("usuarios",usuarios);
        model.addAttribute("titulo","Clientes");
        return "listaUsuarios";
    }
}
