package com.example.booking.repository;

import com.example.booking.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    @Query(value = "SELECT hotel.ciudad, reserva.fecha_inicio, reserva.fecha_fin" +
            "FROM hotel  " +
            "JOIN habitacion  ON hotel.id = habitacion.id_hotel" +
            "join reserva on reserva.id_habitacion = habitacion.id" +
            "where hotel.ciudad = :ciudad and reserva.fecha_inicio = :fecha_inicio and reserva.fecha_fin = :fecha_fin", nativeQuery = true)

    public List<Hotel> search(@Param("ciudad")String ciudades, @Param("fecha_inicio")Date fechaInicio, @Param("fecha_fin") Date fechaFin);

}
