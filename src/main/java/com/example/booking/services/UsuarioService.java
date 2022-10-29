package com.example.booking.services;

import com.example.booking.models.Usuario;
import com.example.booking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;



    public void save(Usuario usuario) { usuarioRepository.save(usuario);
    }

    public Usuario usuarioPorId(Integer id){return usuarioRepository.findById(id).orElse(null);}

    public Usuario buscarPorMail(String mail){return usuarioRepository.buscarPormail(mail);}

    public Usuario usuarioPorNombre(String nombre){
        return usuarioRepository.usuarioPorNombre(nombre);
    }


    public String validarEmail(String email){
        return usuarioRepository.validarEmail(email);
    }

    public Usuario datosUsuario(String auth){return usuarioRepository.datosUsuario(auth);}


}
