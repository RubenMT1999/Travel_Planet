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

    public void guardarReserva(Reserva reserva){
        reservaRepository.save(reserva);
    }

    public List<Reserva> obtenerReservaUsuario(Integer idUsuario){return reservaRepository.obtenerReserva(idUsuario);}

    public Reserva obtenerDetallesReserva(Integer idReserva){return reservaRepository.findById(idReserva).orElse(null);}

    public void cancelarReserva( Reserva reserva){ reservaRepository.delete(reserva);
    }

    public void editarPagado(Boolean pagado, Integer id_habitacion){ reservaRepository.editarPagado(pagado, id_habitacion);}

}
