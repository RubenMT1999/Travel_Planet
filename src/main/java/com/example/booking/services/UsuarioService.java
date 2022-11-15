package com.example.booking.services;

import com.example.booking.models.Usuario;
import com.example.booking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager em;

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

    public void editarUsuario(String nombre, String apellidos, String contrasenia, Date fechaNacimiento,
                          String dni, String nacionalidad, String telefono, String email){
        usuarioRepository.editarUsuario(nombre, apellidos, contrasenia, fechaNacimiento, dni, nacionalidad, telefono, email);
    }

    public void borrarUsuario(Usuario usuario){ usuarioRepository.delete(usuario); }
}
