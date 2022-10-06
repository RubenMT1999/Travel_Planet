package com.example.booking.services;


import com.example.booking.models.Reserva;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.ReservaRepository;
import com.example.booking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    public List<Reserva> listar(){
        return reservaRepository.findAll();
    }

}