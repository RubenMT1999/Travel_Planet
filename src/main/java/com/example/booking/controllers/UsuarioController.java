package com.example.booking.controllers;

import com.example.booking.models.Authorities;
import com.example.booking.models.ERoles;
import com.example.booking.models.UserAuth;
import com.example.booking.models.Usuario;
import com.example.booking.repository.AuthoritiesRepository;
import com.example.booking.repository.UserAuthRepository;
import com.example.booking.repository.UsuarioRepository;
import com.example.booking.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Path;
import javax.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {


    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



 /* @GetMapping("/")
    public String index(Model model){
        model.addAttribute("titulo", "Página de Inicio");
        return "index";
    }



    @GetMapping("/registrar")
    public String registro(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("titulo","Registro - Travel Planet");
        model.addAttribute("usuario", usuario);
        return "registro";
    }

    @PostMapping("/registrar")
    public String procesar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Ha habido algún error");
            return "registro";
        }


        String bcryptPassword = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(bcryptPassword);

        UserAuth auth = new UserAuth();
        auth.setUsername(usuario.getNombre());
        auth.setPassword(usuario.getContrasenia());
        auth.setEnabled(1);
        userAuthRepository.save(auth);

        Authorities authorities = new Authorities();
        authorities.setAuthority(ERoles.ROLE_ADMIN.toString());
        authorities.setUser(auth);


        authoritiesRepository.save(authorities);

        model.addAttribute("titulo", "Resultado form");
        usuarioService.save(usuario);
        status.setComplete();
        return "resultado";
    }


}
