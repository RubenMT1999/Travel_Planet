package com.example.booking.repository;

import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    /*@Query(value = "SELECT *" +
            "FROM Hotel " +
            "JOIN habitacion  ON hotel.id = habitacion.id_hotel " +
            "join reserva on reserva.id_habitacion = habitacion.id " +
            "where hotel.ciudad = :ciudad and reserva.fecha_inicio " +
            "not between :fecha_inicio and :fecha_fin and reserva.fecha_fin " +
            "not between :fecha_inicio and :fecha_fin", nativeQuery = true)

     */
    @Query(value = "select * from vistabuscador where ciudad = :ciudad " +
            "and fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin group by nombre", nativeQuery = true)



    List<Hotel> buscador(String ciudad, Date fecha_inicio, Date fecha_fin);
   // Reserva buscar(Date fecha_inicio, Date fecha_fin);



}


