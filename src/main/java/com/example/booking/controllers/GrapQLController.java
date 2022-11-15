package com.example.booking.controllers;

import com.example.booking.models.Usuario;
import com.example.booking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GrapQLController {


//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserAuthRepository userAuthRepository;
//
//    @Autowired
//    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }




//    @PostMapping("")
//    @MutationMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Usuario nuevoUsuario(@RequestBody @Argument(name = "input")GrapqlModels.UsuarioInput input){
//
//        Usuario usuario = new Usuario();
//
//        String bcryptPassword = passwordEncoder.encode(input.getPassword());
//        usuario.setContrasenia(bcryptPassword);
//        usuario.setNombre(input.getNombre());
//        usuario.setApellidos(input.getApellidos());
//        usuario.setFechaNacimiento(input.getFechaNacimiento());
//        usuario.setNacionalidad(input.getNacionalidad());
//        usuario.setDni(input.getDni());
//        usuario.setEmail(input.getEmail());
//        usuario.setEsHotelero(input.getEsHotelero());
//        usuario.setTelefono(input.getTelefono());
//
//        UserAuth auth = new UserAuth();
//        auth.setUsername(usuario.getEmail());
//        auth.setPassword(usuario.getContrasenia());
//        auth.setEnabled(1);
//        userAuthRepository.save(auth);
//
//        Authorities authorities = new Authorities();
//
//        if(usuario.getEsHotelero() == true){
//            authorities.setAuthority(ERoles.ROLE_ADMIN.toString());
//        }else {
//            authorities.setAuthority(ERoles.ROLE_USER.toString());
//        }
//
//        authorities.setUser(auth);
//        authoritiesRepository.save(authorities);
//        return usuarioRepository.save(usuario);
//    }

}
