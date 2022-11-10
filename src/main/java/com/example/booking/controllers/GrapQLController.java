package com.example.booking.controllers;

import com.example.booking.models.*;
import com.example.booking.repository.AuthoritiesRepository;
import com.example.booking.repository.UserAuthRepository;
import com.example.booking.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/graphql")
public class GrapQLController {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @PostMapping("/nuevo/usuario")
    @QueryMapping
    public Usuario nuevoUsuario(@RequestBody @Argument(name = "input")GrapqlModels.UsuarioInput input){

        Usuario usuario = new Usuario();

        String bcryptPassword = passwordEncoder.encode(input.getPassword());
        usuario.setContrasenia(bcryptPassword);
        usuario.setNombre(input.getNombre());
        usuario.setApellidos(input.getApellidos());
        usuario.setFechaNacimiento(input.getFechaNacimiento());
        usuario.setNacionalidad(input.getNacionalidad());
        usuario.setDni(input.getDni());
        usuario.setEmail(input.getEmail());
        usuario.setEsHotelero(input.getEsHotelero());

        UserAuth auth = new UserAuth();
        auth.setUsername(usuario.getEmail());
        auth.setPassword(usuario.getContrasenia());
        auth.setEnabled(1);
        userAuthRepository.save(auth);

        Authorities authorities = new Authorities();

        if(usuario.getEsHotelero() == true){
            authorities.setAuthority(ERoles.ROLE_ADMIN.toString());
        }else {
            authorities.setAuthority(ERoles.ROLE_USER.toString());
        }

        authorities.setUser(auth);
        authoritiesRepository.save(authorities);
        usuarioService.save(usuario);
        return usuario;
    }

}
