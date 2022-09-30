package com.example.booking.controllers;

import com.example.booking.models.Usuario;
import com.example.booking.repository.UsuarioRepository;
import com.example.booking.services.IUsuarioService;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {


    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;



    @GetMapping("/login")
    public String inicioSesion(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("titulo","Inicio de Sesión");
        model.addAttribute("usuario",usuario);
        return "login";
    }



    @PostMapping("/login/{email}/{password}")
    public String procesarLogin(@PathVariable String email, @PathVariable String password,
                                @Valid Usuario usuario, BindingResult result, Model model, SessionStatus status){


        try{
            usuario = usuarioRepository.findUserByNameAndPassword(email,password);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(result.hasErrors() || usuario.getEmail().isEmpty()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "login";
        }




        model.addAttribute("titulo","Inicio de Sesion - Travel Planet");
        model.addAttribute("usuario", usuario);
        status.setComplete();
        return "resultado";

    }





   /* @GetMapping("/login/{nombre}/{password}")
    public String login(@PathVariable String nombre, @PathVariable String password ,Model model){
        Usuario usuario = new Usuario();

        try{
            usuario = usuarioRepository.findUserByNameAndPassword(nombre,password);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        model.addAttribute("titulo","Registro - Travel Planet");
        model.addAttribute("usuario", usuario);
        return "resultado";
    }*/





    @GetMapping("/registrar")
    public String registro(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("titulo","Registro - Travel Planet");
        model.addAttribute("usuario", usuario);
        return "registro";
    }

    @PostMapping("/registrar")
    public String procesar(@Valid Usuario usuario, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "registro";
        }



        return "redirect:/ver";

    }

    @GetMapping("/ver")
    public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario, Model model, SessionStatus status) {

        if(usuario == null) {
            return "redirect:/registrar";
        }

        model.addAttribute("titulo", "Resultado form");

        usuarioService.save(usuario);
        status.setComplete();
        return "resultado";
    }


}
