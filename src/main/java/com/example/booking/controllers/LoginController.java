package com.example.booking.controllers;

import com.example.booking.models.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collection;

@Controller
public class LoginController {

    //requestParam por que al hacer login si no está registrado nos manda un parametro "error" por la url.
    //Lo ponemos en required false por que no siempre nos lo manda.
    @GetMapping("/login")
    public String login(@RequestParam(value ="error", required = false) String error,
                        @RequestParam(value ="logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash){

        //si principal es distinto a null es porque ya ha iniciado sesion anteriormente
        //para que no nos muestre otra vez el login hacemos esto.
        if(principal != null){
            flash.addAttribute("info","Ya ha iniciado sesión anteriormente");

            return "redirect:/";
        }

        if(error != null){
            model.addAttribute("error","Error en el login. Nombre o contraseña incorrecta. Vuelva a intentarlo");
        }

        if (logout != null){
            model.addAttribute("success","Ha cerrado sesión con éxito");
        }

        model.addAttribute("titulo","Inicio de Sesión");
        return "login";
    }




    /* Obtener el role del autenticado */
//    private boolean hasRole(String role){
//        SecurityContext context = SecurityContextHolder.getContext();
//
//        if (context == null){
//            return false;
//        }
//
//        Authentication auth = context.getAuthentication();
//
//        if (auth == null){
//            return false;
//        }
//
//        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//
//        return authorities.contains(new SimpleGrantedAuthority(role));
//    }


}
