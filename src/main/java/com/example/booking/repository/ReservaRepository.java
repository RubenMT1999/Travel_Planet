package com.example.booking.repository;

import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservaRepository  extends JpaRepository<Reserva,Integer> {

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = ?1")
    List<Reserva> obtenerReserva(Integer idUsuario);

    @Query("SELECT r FROM Reserva r WHERE r.usuario.nombre = ?1")
    List<Reserva> reservasPorNombre(String nombre);

    @Modifying
    @Transactional
    @Query("UPDATE Reserva SET pagado = :pagado where habitacion.id = :id_habitacion")
    void editarPagado(Boolean pagado, Integer id_habitacion);


}
