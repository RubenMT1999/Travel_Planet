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


    @Query("SELECT r FROM Reserva r WHERE r.usuario.nombre = ?1")
    List<Reserva> reservasPorNombre(String nombre);

    @Query(value = "SELECT fecha_inicio FROM vistahabitacion where num_habitacion = :num_hab", nativeQuery = true)
    Date fechaInicioReserva(Integer num_hab);
    @Query(value = "SELECT fecha_fin FROM vistahabitacion where num_habitacion = :num_hab", nativeQuery = true)
    Date fechaFinReserva(Integer num_hab);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO reserva(id_usuario, id_habitacion, fecha_inicio, fecha_fin, metodo_pago)" +
            "values (?1, ?2, ?3, ?4, ?5)",nativeQuery = true)
    Reserva guardarReserva(Integer id_usuario, Integer id_habitacion, Date fecha_incio, Date fecha_fin, String metodo_pago);



}
