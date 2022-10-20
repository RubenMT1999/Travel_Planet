package com.example.booking.services;

import com.example.booking.models.Usuario;
import com.example.booking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario usuarioPorId(Integer id){return usuarioRepository.findById(id).orElse(null);}

    public Usuario buscarPorMail(String mail){return usuarioRepository.buscarPormail(mail);}





}
