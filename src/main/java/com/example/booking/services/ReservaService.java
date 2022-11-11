package com.example.booking.services;


import com.example.booking.models.Reserva;
import com.example.booking.repository.HabitacionRepository;
import com.example.booking.repository.ReservaRepository;
import com.example.booking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        Reserva reserva = new Reserva();
        return reservaRepository.findAll();
    }


    public List<Reserva> reservasPorNombre(String nombre) {
        return reservaRepository.reservasPorNombre(nombre);
    }

    public Date fechaInicioReserva(Integer num_hab){return reservaRepository.fechaInicioReserva(num_hab);}
    public Date fechaFinReserva(Integer num_hab){return reservaRepository.fechaFinReserva(num_hab);}

    public Reserva reserva(Integer id_usuario, Integer id_habitacion, Date fecha_incio, Date fecha_fin, String metodo_pago)
    {return reservaRepository.guardarReserva(id_usuario,id_habitacion,fecha_incio,fecha_fin,metodo_pago);}
}
