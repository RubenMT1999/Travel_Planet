package com.example.booking.controllers;

import com.example.booking.models.Hotel;
import com.example.booking.models.Usuario;
import com.example.booking.services.HotelService;
import com.example.booking.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
    public class HotelController {
    @Autowired
    private HotelService hotelservice;




    @GetMapping("/hotel")
    public String hotel(Model model){
        List<Hotel> hotels = new ArrayList<>();
        hotels = hotelservice.listAll("Sevilla",Date.valueOf("2022-09-09"), Date.valueOf("2022-10-10"));
        model.addAttribute("titulo","Registro - Travel Planet");

        model.addAttribute("hotel", hotels);
        return "resultado1";
    }

}
