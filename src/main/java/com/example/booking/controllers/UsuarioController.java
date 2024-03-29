package com.example.booking.controllers;

import com.example.booking.models.Authorities;
import com.example.booking.models.ERoles;
import com.example.booking.models.UserAuth;
import com.example.booking.models.Usuario;
import com.example.booking.repository.AuthoritiesRepository;
import com.example.booking.repository.UserAuthRepository;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Path;
import javax.validation.Valid;
import java.util.Collections;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/registrar")
    public String registro(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("titulo","Registro - Travel Planet");
        model.addAttribute("usuario", usuario);
        long anios = 16;
        LocalDate maximo = LocalDate.now();
        maximo = maximo.minusYears(anios);
        model.addAttribute("maximo", maximo);

        return "registro";
    }

    @PostMapping("/registrar")
    public String procesar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Ha habido algún error");
            return "registro";
        }

        if (usuario.getContrasenia().length()>20){
            model.addAttribute("errorContrasenia","La contraseña no puede tener más de 20 caracteres");
            return "registro";
        }

        if (usuarioService.validarEmail(usuario.getEmail()) != null){
            model.addAttribute("errorCorreo","El correo electrónico ya está en uso.");
            return "registro";
        }

        String bcryptPassword = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(bcryptPassword);

        UserAuth auth = new UserAuth();
        auth.setUsername(usuario.getEmail());
        auth.setPassword(usuario.getContrasenia());
        auth.setEnabled(1);
        userAuthRepository.save(auth);

        Authorities authorities = new Authorities();

        if(usuario.getEsHotelero()){
            authorities.setAuthority(ERoles.ROLE_ADMIN.toString());
        }else {
            authorities.setAuthority(ERoles.ROLE_USER.toString());
        }

        authorities.setUser(auth);
        authoritiesRepository.save(authorities);

        model.addAttribute("titulo", "Resultado form");
        usuarioService.save(usuario);
        status.setComplete();



//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(auth.getUsername(),
//                auth.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(ERoles.ROLE_ADMIN.toString())));
//
//        authToken.setDetails(new WebAuthenticationDetails(request));
//
//        SecurityContextHolder.getContext().setAuthentication(authToken);

        return "redirect:/";

    }

    }
